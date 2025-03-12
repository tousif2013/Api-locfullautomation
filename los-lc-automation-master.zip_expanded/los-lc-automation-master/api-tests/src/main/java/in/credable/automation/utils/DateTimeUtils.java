package in.credable.automation.utils;

import java.time.Instant;

public final class DateTimeUtils {
    private DateTimeUtils() {
    }

    public static long getCurrentTimeInSeconds() {
        return Instant.now().getEpochSecond();
    }

    public static long getCurrentTimeInNano() {
        return Instant.now().getNano();
    }
}
