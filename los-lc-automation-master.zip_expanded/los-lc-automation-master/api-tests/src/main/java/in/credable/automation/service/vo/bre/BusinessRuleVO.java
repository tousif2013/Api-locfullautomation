package in.credable.automation.service.vo.bre;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import uk.co.jemos.podam.common.PodamIntValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BusinessRuleVO {
    @JsonProperty("id")
    private String id;

    @JsonProperty("version")
    private String version;

    @JsonValue(value = false)
    private boolean async;

    @PodamIntValue(numValue = "1")
    private int sequence;

}