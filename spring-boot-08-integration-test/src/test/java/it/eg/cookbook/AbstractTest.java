package it.eg.cookbook;

import it.eg.cookbook.util.TestRestUtil;
import it.eg.cookbook.util.TestType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Classe comune per la gestione di Unit e Integration test.
 * Nota:
 * - gli Unit Test attivano il contesto spring per cui tutte le annotazioni Spring (ad es. @Value, @Autowired, etc.) sono attive
 * - gli Intetìgration Test NON attivano il contesto spring: l'applicazione gira su container
 */
@Slf4j
public abstract class AbstractTest {


    private static boolean firstTest = true;

    private TestInfo testInfo;

    protected RestTemplate restTemplate = new RestTemplate();

    // In caso di Unit test il Data Source è impostato da Spring
    @Autowired
    protected DataSource dataSource;

    // In caso di Integration test il Data Source deve essere inizializzato
    private static final DataSource IT_DATA_SOURCE =
            new DriverManagerDataSource("jdbc:postgresql://localhost:5432/postgres?currentSchema=cookbook", "cookbook", "cookbook");

    @BeforeEach
    void setup(TestInfo testInfo) throws SQLException {
        this.testInfo = testInfo;

        if (firstTest) {
            // Operazioni da eseguire allo startup
            firstTest = false;
        } else {
            dataSource = IT_DATA_SOURCE;
        }

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


}
