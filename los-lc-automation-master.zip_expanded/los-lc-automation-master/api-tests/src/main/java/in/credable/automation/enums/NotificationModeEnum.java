package in.credable.automation.enums;

import in.credable.automation.config.ConfigFactory;
import lombok.Getter;

@Getter
public enum NotificationModeEnum {
    SMS(ConfigFactory.getEnvironmentConfig().getNotificationModeForSMS()),
    EMAIL(ConfigFactory.getEnvironmentConfig().getNotificationModeForEmail());

    private final int notificationModeId;

    NotificationModeEnum(int notificationModeId) {
        this.notificationModeId = notificationModeId;
    }
}
