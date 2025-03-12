package in.credable.automation.service.vo.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import in.credable.automation.attributestrategy.UuidStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamBooleanValue;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "vendorId",
        "clientId",
        "sourceReferenceId",
        "isTemplateRequired",
        "to",
        "subject",
        "content",
        "attachmentDocUrls"
})
@Data
public class MessageVO {

    @JsonProperty("vendorId")
    @PodamExclude
    private String vendorId;

    @JsonProperty("clientId")
    @PodamStringValue(strValue = "SEND_GRID")
    private String clientId;

    @JsonProperty("sourceReferenceId")
    @PodamStrategyValue(value = UuidStrategy.class)
    private String sourceReferenceId;

    @JsonProperty("templateRequired")
    @PodamBooleanValue
    private Boolean templateRequired;

    @JsonProperty("to")
    @PodamExclude
    private List<String> to;

    @JsonProperty("subject")
    @PodamStringValue(strValue = "Loan Application Update")
    private String subject;

    @JsonProperty("content")
    @PodamStringValue(strValue = "Dear customer, your loan application has been approved.")
    private String content;

    @JsonProperty("attachmentDocUrls")
    @PodamExclude
    private List<String> attachmentDocUrls;

}
