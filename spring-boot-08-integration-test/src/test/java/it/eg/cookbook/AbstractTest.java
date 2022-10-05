package it.eg.cookbook;

import it.eg.cookbook.util.TestRestUtil;
import it.eg.cookbook.util.TestType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.time.Duration;

/**
 * Classe comune per la gestione di Unit e Integration test.
 * Nota:
 * - gli Unit Test attivano il contesto spring per cui tutte le annotazioni Spring (ad es. @Value, @Autowired, etc.) sono attive
 * - gli Intetìgration Test NON attivano il contesto spring: l'applicazione gira su container
 */
@Slf4j
public abstract class AbstractTest {

    private TestInfo testInfo;

    private static boolean firstTest = true;

    protected RestTemplate restTemplate;

    protected JdbcTemplate jdbcTemplate;

    // In caso di Unit test il Data Source è impostato da Spring
    @Autowired
    protected DataSource dataSource;

    // In caso di Integration test il Data Source deve essere inizializzato
    private static final DataSource IT_DATA_SOURCE =
            new DriverManagerDataSource("jdbc:postgresql://localhost:5433/postgres?currentSchema=cookbook", "cookbook", "cookbook");

    @BeforeEach
    void setup(TestInfo testInfo) throws SQLException {
        this.testInfo = testInfo;

        // Operazioni da eseguire allo startup
        if (firstTest) {
            // Initegration test
            if (getTestType() == TestType.IntegrationTest) {
                // Imposto il datasource
                dataSource = IT_DATA_SOURCE;

                // Avvio il compose
                startCompose();
            }

            firstTest = false;
        }

        restTemplate = new RestTemplate();
        jdbcTemplate = new JdbcTemplate(dataSource);

        // Operazioni da eseguire ad ogni test
    }

    public TestType getTestType() {
        return testInfo.getTestClass().get().getSimpleName().endsWith("IT") ? TestType.IntegrationTest : TestType.UnitTest;
    }

    public String getTestClass() {
        return testInfo.getTestClass().get().getSimpleName();
    }

    public String getTestMethod() {
        return testInfo.getTestMethod().get().getName();
    }

    private void startCompose() {
        // Singleton container:
        // https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/
        //
        // 16/09/2022: --compatibility affinché Testcontainers riconosca
        // l'avvio dei servizi anche su versioni recenti di docker-compose
        // che userebbero il separatore "-" anziché "_" nei nomi container:
        // https://techhelpnotes.com/docker-compose-container-name-use-dash-instead-of-underscore-_-2/
        // Nessun problema su Windows con docker-compose 1.29.2 bensì su:
        // - Linux: docker-compose 1.26.0 & Docker Compose v2.6.0
        // - macOS: Docker Compose v2.10.2
        new DockerComposeContainer<>(new File("docker/docker-compose.yml"))
                .withOptions("--compatibility")
                .withExposedService("postgres", 5433, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))
                .withExposedService("rest-api", 8082, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))
                .withBuild(true)
                .withLocalCompose(true)
                .start();
    }

//    private static class DockerComposeSingletonLauncher {
//        static {
//            log.info("Avviamo docker-compose");
//
//            // Singleton container:
//            // https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/
//            //
//            // 16/09/2022: --compatibility affinché Testcontainers riconosca
//            // l'avvio dei servizi anche su versioni recenti di docker-compose
//            // che userebbero il separatore "-" anziché "_" nei nomi container:
//            // https://techhelpnotes.com/docker-compose-container-name-use-dash-instead-of-underscore-_-2/
//            // Nessun problema su Windows con docker-compose 1.29.2 bensì su:
//            // - Linux: docker-compose 1.26.0 & Docker Compose v2.6.0
//            // - macOS: Docker Compose v2.10.2
//            new DockerComposeContainer<>(new File("docker/docker-compose.yml"))
//                    .withOptions("--compatibility")
//                    .withExposedService("gaat-mysql", 3306, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))
//                    .withExposedService("be-piattaforma-unica", 8080, Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(120)))
//                    .withBuild(true)
//                    .withLocalCompose(true)
//                    .start();
//        }
//    }


}
