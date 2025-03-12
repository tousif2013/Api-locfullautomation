package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Data
public class PANVerificationVO {
    @PodamStringValue(strValue = "AAABB0001C")
    private String businessPAN;
    @PodamExclude
    private boolean valid;
    @PodamExclude
    private String type;
}
