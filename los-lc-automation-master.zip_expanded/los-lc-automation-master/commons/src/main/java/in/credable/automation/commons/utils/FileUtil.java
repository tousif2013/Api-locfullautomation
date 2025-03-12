package in.credable.automation.commons.utils;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Log4j2
public final class FileUtil {
    private FileUtil() {
    }

    public static File convertStringToTempFile(String fileNameWithExtension, String data) {
        File tempFile = createTempFile(fileNameWithExtension);
        try {
            FileUtils.writeStringToFile(tempFile, data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Error in writing string to temp file", e);
        }
        return tempFile;
    }

    @SneakyThrows
    public static File createTempFile(String fileNameWithExtension) {
        String tempDirectory = getTempDirectoryPath();
        File file = new File(tempDirectory + File.separator + fileNameWithExtension);
        if (file.createNewFile()) {
            log.info("File created in temporary directory: " + file.getAbsolutePath());
        } else {
            log.error("Could not create file in temp directory");
        }
        return file;
    }

    public static String getTempDirectoryPath() {
        return FileUtils.getTempDirectory().getAbsolutePath();
    }
}
