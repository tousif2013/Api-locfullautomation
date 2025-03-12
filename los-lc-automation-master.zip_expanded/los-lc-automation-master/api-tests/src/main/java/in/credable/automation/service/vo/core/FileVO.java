package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStringValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "type",
        "content"
})
@Data
public class FileVO {
    @JsonProperty("name")
    @PodamStringValue(strValue = "D5_7 lakh offer.pdf")
    private String name;

    @JsonProperty("type")
    @PodamStringValue(strValue = "application/pdf")
    private String type;

    @JsonProperty("content")
    @PodamStringValue(strValue = "documents/bankstatement/D5_7 lakh offer.pdf")
    private String content;
}
