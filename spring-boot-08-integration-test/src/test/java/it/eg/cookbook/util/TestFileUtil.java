package it.eg.cookbook.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;

@Data
@Slf4j
public class TestFileUtil {

    private TestType testType;
    private String className;
    private String methodName;

    public TestFileUtil(TestInfo testInfo) {
        testType = testInfo.getTestClass().get().getSimpleName().endsWith("IT") ? TestType.IntegrationTest : TestType.UnitTest;
        className = testInfo.getTestClass().get().getSimpleName();
        methodName = testInfo.getTestMethod().get().getName();
    }


    protected static String readFile(String fileName) {
        return readFile(new File(fileName));
    }

    protected String readMockFile() {
        return readMockFile("");
    }

    protected String readMockFile(String fileNameSuffix) {
        return readFile("/mock/", fileNameSuffix);
    }

    protected String readExpectedFile() {
        return readExpectedFile("");
    }

    protected String readExpectedFile(String fileNameSuffix) {
        return readFile("/expected/", fileNameSuffix);
    }

    protected String readRequestFile() {
        return readRequestFile("");
    }

    protected String readRequestFile(String fileNameSuffix) {
        return readFile("/request/", fileNameSuffix);
    }

    protected String readModelFile() {
        return readModelFile("");
    }

    protected String readModelFile(String fileNameSuffix) {
        return readFile("/model/", fileNameSuffix);
    }


    public String readFile(String type, String fileNameSuffix) {
        String fileNamePath = "src/test/file/{0}{1}{2}{3}.json";

        // Lettura file specifico per il test (in funzione del tipo ti test UT o IT)
        File file = new File(MessageFormat.format(fileNamePath, className, type, methodName, fileNameSuffix));
        if (file.isFile()) {
            return readFile(file);
        }

        // Lettura file generico per il test (indipendente dal fatto che siano UT o IT)
        className = className.endsWith("IT") ? className.substring(0, className.length() - 2) : className.substring(0, className.length() - 4);
        file = new File(MessageFormat.format(fileNamePath, className, type, methodName, fileNameSuffix));

        return readFile(file);
    }

    public static String readFile(File readFile) {
        if (!readFile.isFile()) {
            Assertions.fail("File " + readFile + " non leggibile");
        }

        if (!readFile.exists()) {
            Assertions.fail("File " + readFile + " non trovato");
        }

        try {
            return FileUtils.readFileToString(readFile, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            Assertions.fail("File " + readFile + " non leggibile", ex);
            return null;
        }
    }

}
