package in.credable.automation.service.vo.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStringValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "topic_name",
        "message"
})
@Data
public class EmailNotificationVO {

    @JsonProperty("topic_name")
    @PodamStringValue(strValue = "email_topic")
    private String topicName;

    @JsonProperty("message")
    private MessageVO message;

}
