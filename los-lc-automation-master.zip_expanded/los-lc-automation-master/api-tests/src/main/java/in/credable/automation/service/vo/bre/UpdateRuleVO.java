package in.credable.automation.service.vo.bre;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UpdateRuleVO {
    @JsonProperty("upper_threshold")
    private String upperThreshold;
    @JsonProperty("lower_threshold")
    private String lowerThreshold;

}
