package it.eg.cookbook;

import it.eg.cookbook.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
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

    private static boolean firstTest = true;

    private TestFileUtil testFileUtil;


    // In caso di Unit test il Data Source è impostato da Spring
    @Autowired
    protected DataSource dataSource;

    // In caso di Integration test il Data Source deve essere inizializzato
    private static final DataSource IT_DATA_SOURCE =
            new DriverManagerDataSource("jdbc:postgresql://localhost:5432/postgres?currentSchema=cookbook", "cookbook", "cookbook");

    @BeforeEach
    void setup(TestInfo testInfo) throws SQLException {
        this.testFileUtil = new TestFileUtil(testInfo);

        if (firstTest) {
            // Operazioni da eseguire allo startup
            firstTest = false;
        } else {
            dataSource = IT_DATA_SOURCE;
        }

        // Operazioni da eseguire ad ogni test
    }


//        WireMock.resetToDefault();
//        setupWireMockDefaultStubs();
//
//        // Setup DB
//        if (dataSource == null) {                       // No Spring
//            if (getClass().getName().endsWith("IT")) {  // Integration test
//                dataSource = IT_DATA_SOURCE;
//                new DockerComposeSingletonLauncher();
//            }
//        } else if (dataSource != IT_DATA_SOURCE) { // Gli IT hanno il DB prepopolato
//            populateDatabase(dataSource);
//        }


//    /**
//     * L'endpoint del back end di Piattaforma Unica.
//     */
//    protected static final String PIATTAFORMA_UNICA_ENDPOINT = "http://localhost:8080";
//
//    /**
//     * A uso e consumo delle sottoclassi, per effettuare chiamate REST.
//     */
//    protected RestTemplate restTemplate = new RestTemplate();
//
//
//    ObjectMapper mapper = JsonMapper
//            .builder()
//            .addModules(new Jdk8Module(), new JavaTimeModule(), new ParameterNamesModule())
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
//            .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
//            .enable(SerializationFeature.INDENT_OUTPUT)
//            .serializationInclusion(JsonInclude.Include.NON_NULL)
//            // E' necessario non serilizzare i campi non valorizzati nell'integrazione con Feign
//            //.serializationInclusion(JsonInclude.Include.ALWAYS)
//            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
//            .defaultTimeZone(TimeZone.getDefault())
//            .build();
//    private TestInfo testInfo;

//    static {
//        WireMockServer wmServer = new WireMockServer(options().port(8081));
//
//        // WireMock dev'essere pronto <<<PRIMA>>> che la cache dei domini sia
//        // popolata all'avvio di Spring tramite @PostConstruct
//        wmServer.start();
//        WireMock.configureFor(new WireMock(wmServer));
//        setupWireMockDefaultStubs();
//    }
//
//    private static void setupWireMockDefaultStubs() {
//
//        // Setup mock backoffice LisaWeb - http://backoffice.lisawebonline.com/api/login -> localhost:8081/backoffice/api/login
//        stubFor(post(urlPathEqualTo("/backoffice/api/login"))
//                .withQueryParam("username", equalTo("backoffice-username"))
//                .withQueryParam("password", equalTo("backoffice-password"))
//                .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/backoffice-response-login.json"))));
//
//        // Setup mock backoffice LisaWeb - http://backoffice.lisawebonline.com/api/importers/setup -> localhost:8081/backoffice/api/importers/setup
//        stubFor(get(urlPathEqualTo("/backoffice/api/importers/setup"))
//                .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/backoffice-response-domini.json"))));
//
//
//        // Setup mock frontoffice LisaWeb - http://frontoffice.lisawebonline.com/login -> localhost:8081/frontoffice/login
//        stubFor(post(urlPathEqualTo("/frontoffice/login"))
//                .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/frontoffice-response-login.json"))));
//
//        // Setup mock frontoffice LisaWeb - https://frontoffice.lisawebonline.com/api/councils -> localhost:8081/frontoffice/api/councils
//        stubFor(get(urlPathEqualTo("/frontoffice/councils"))
//                .withQueryParam("perPage", equalTo("0"))
//                .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/front-office-response-councils.json"))));
//
//        // Setup mock frontoffice LisaWeb - https://frontoffice.lisawebonline.com/api/provinces -> localhost:8081/frontoffice/api/provinces
//        stubFor(get(urlPathEqualTo("/frontoffice/provinces"))
//                .withQueryParam("perPage", equalTo("0"))
//                .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/frontoffice-response-provinces.json"))));
//
//        for (int i = 1; i <= 8; i++) {
//            stubFor(get(urlEqualTo("/frontoffice/registries/" + i))
//                    .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
//                    .withHeader("Agenzia", equalTo("gt001"))
//                    .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/frontoffice-response-registry-gt001-" + i +".json"))));
//
//            stubFor(get(urlEqualTo("/frontoffice/registries/" + i))
//                    .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
//                    .withHeader("Agenzia", equalTo("gt002"))
//                    .willReturn(RestUtil.responseBody(readFile("src/test/common-mock/frontoffice-response-registry-gt002-" + i +".json"))));
//        }
//
//
//    }


    //    protected static String readFile(String fileName) {
//        return readFile(new File(fileName));
//    }
//
//    protected String readMockFile() {
//        return readMockFile("");
//    }
//
//    protected String readMockFile(String fileNameSuffix) {
//        return readFile("/mock/", fileNameSuffix);
//    }
//
    protected String readExpectedFile() {
        return readExpectedFile("");
    }

    protected String readExpectedFile(String fileNameSuffix) {
        return testFileUtil.readFile("/expected/", fileNameSuffix);
    }
//
//    protected String readRequestFile() {
//        return readRequestFile("");
//    }
//
//    protected String readRequestFile(String fileNameSuffix) {
//        return readFile("/request/", fileNameSuffix);
//    }
//
//    protected String readModelFile() {
//        return readModelFile("");
//    }
//
//    protected String readModelFile(String fileNameSuffix) {
//        return readFile("/model/", fileNameSuffix);
//    }
//
//
//
//    private String readFile(String dirSuffix, String fileNameSuffix) {
//        String fileNamePath = "src/test/json/{0}{1}{2}{3}.json";
//
//        String className = testInfo.getTestClass().get().getSimpleName();
//        String methodName = testInfo.getTestMethod().get().getName();
//
//        // Lettura file specifico per il test (in funzione del tipo ti test UT o IT)
//        File file = new File(MessageFormat.format(fileNamePath, className, dirSuffix, methodName, fileNameSuffix));
//        if (file.isFile()) {
//            return readFile(file);
//        }
//
//        // Lettura file generico per il test (indipendente dal fatto che siano UT o IT)
//        className = className.endsWith("IT") ? className.substring(0, className.length() - 2) : className.substring(0, className.length() - 4);
//        file = new File(MessageFormat.format(fileNamePath, className, dirSuffix, methodName, fileNameSuffix));
//        if (file.isFile()) {
//            return readFile(file);
//        }
//
//        fail("File " + file + (file.exists() ? " non leggibile" : " non trovato"));
//
//        // src
//        // └── test
//        //     └── json
//        //         └── AnagraficaController
//        //             ├── mock
//        //             │   ├── testRicercaAnagrafiche.json
//        //             │   ├── testRicercaAnagrafiche_registry.json
//        //             └── expected
//        //                 └── testRicercaAnagrafiche.json
//        //         └── AnagraficaControllerTest
//        //             ├── mock
//        //             │   ├── testRicercaAnagrafiche.json
//        //             │   ├── testRicercaAnagrafiche_registry.json
//        //             │   ├── testRicercaAnagrafiche_addressbook.json
//        //             │   ├── testRicercaAnagrafiche_correspondences_1.json
//        //             │   └── testRicercaAnagrafiche_correspondences_2.json
//        //             │
//        //             └── expected
//        //                 └── testRicercaAnagrafiche.json
//        File f = new File(
//                "src/test/json/"
//                        + testInfo.getTestClass().get().getSimpleName()
//                        + dirSuffix
//                        + testInfo.getTestMethod().get().getName() + fileNameSuffix + ".json");
//
//        return readFile(f);
//    }
//
//    private static String readFile(File readFile) {
//        if (readFile.isFile()) {
//            try {
//                return FileUtils.readFileToString(readFile, StandardCharsets.UTF_8);
//            } catch (IOException ex) {
//                log.error(ex.getMessage(), ex);
//                fail(ex);
//            }
//        }
//        fail("File " + readFile + (readFile.exists() ? " non leggibile" : " non trovato"));
//        return null;            // Per compilazione. Non arriveremo mai qui
//    }
//
//    protected void assertJsonEquals(String expectedStr, String actualStr) {
//        try {
//            try {
//                // "STRICT" pro fallimento test in presenza di campi aggiuntivi
//                JSONAssert.assertEquals(expectedStr, actualStr, JSONCompareMode.STRICT);
//            } catch (AssertionError ex) {
//                String fileNamePath = "./target/actual/{0}/{1}.json";
//                String className = testInfo.getTestClass().get().getSimpleName();
//                String methodName = testInfo.getTestMethod().get().getName();
//
//                FileUtils.writeStringToFile(new File(MessageFormat.format(fileNamePath, className, methodName)), actualStr, StandardCharsets.UTF_8);
//                fail(ex);
//            }
//        } catch (JSONException | IOException ex) {
//            log.error(ex.getMessage(), ex);
//            fail(ex);
//        }
//    }

//    @SuppressWarnings("resource")
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
