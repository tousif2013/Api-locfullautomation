package in.credable.automation.utils;

import in.credable.automation.commons.utils.FileUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public final class TestUtils {
    private TestUtils() {
    }

    public static String extractFileNameFromResponseHeader(Response response) {
        String contentDisposition = response.getHeader("content-disposition");
        return StringUtils.substringBetween(contentDisposition, "\"", "\"");
    }

    @SneakyThrows
    public static File convertResponseToTempFile(Response response, String fileNameWithExtension) {
        InputStream responseInputStream = response.asInputStream();
        File tempFile = FileUtil.createTempFile(fileNameWithExtension);
        FileUtils.copyInputStreamToFile(responseInputStream, tempFile);
        return tempFile;
    }

    /**
     * @param response                The {@link Response} object
     * @param pathToSchemaInClasspath Json schema file path in classpath.
     *                                Note: schema file must be in classpath.
     *                                e.g. src/test/resources/schema/user.json
     */
    public static void validateJsonSchemaInClasspath(Response response, String pathToSchemaInClasspath) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(pathToSchemaInClasspath));
    }


    /**
     * return the file object from the specified file path resources root
     *
     * @param filePathFromResourcesRoot
     * @return File
     */
    @SneakyThrows
    public static File getFileFromResources(String filePathFromResourcesRoot) {
        URL resource = TestUtils.class.getClassLoader().getResource(filePathFromResourcesRoot);
        return new File(Objects.requireNonNull(resource).toURI());
    }

    /**
     * Substitutes variables in a string using values from a provided map.
     *
     * @param input
     * @param map
     * @return String
     */
    public static String substituteString(String input, Map<String, Object> map) {
        StringSubstitutor substitutor = new StringSubstitutor(map);
        return substitutor.replace(input);
    }


}
