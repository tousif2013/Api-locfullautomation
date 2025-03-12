package in.credable.automation.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public final class LoggerUtil {
    private static final String MESSAGE_SEPARATOR = "=================================================================";
    private LoggerUtil() {
    }

    public static void printLog(Logger logger, Level level, String message, Object... params) {
        logger.log(level, MESSAGE_SEPARATOR);
        logger.log(level, message, params);
        logger.log(level, MESSAGE_SEPARATOR);
    }
}
