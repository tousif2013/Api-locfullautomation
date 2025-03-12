package in.credable.automation.service.vo.bre;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import uk.co.jemos.podam.common.PodamIntValue;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ExecuteBusinessRuleVO {
    @PodamIntValue(numValue = "1")
    private String moduleInstanceID;

    @JsonProperty("businessRules")
    private List<BusinessRuleVO> businessRules;

    @JsonProperty("inputData")
    private InputDataVO inputData;

    @JsonProperty("timestamp")
    private Date timestamp;
}