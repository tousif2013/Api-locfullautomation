package in.credable.automation.service.vo.client;


import in.credable.automation.enums.NotificationModeEnum;
import lombok.Data;

@Data
public class NotificationModeVO {
    private Long id;
    private NotificationModeEnum mode;
}
