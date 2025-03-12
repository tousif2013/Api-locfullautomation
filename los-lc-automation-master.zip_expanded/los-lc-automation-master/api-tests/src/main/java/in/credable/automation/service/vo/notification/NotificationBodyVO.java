package in.credable.automation.service.vo.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotificationBodyVO {
    @JsonProperty("message")
    private String message;

    @JsonProperty("topic_name")
    private String topicName;
}
