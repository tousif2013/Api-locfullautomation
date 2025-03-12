package in.credable.automation.service.vo.bre;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class InputDataVO {
    @JsonProperty("recommendedLimit")
    private int recommendedLimit;
}