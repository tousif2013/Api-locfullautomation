package in.credable.automation.service.vo.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uk.co.jemos.podam.common.PodamBooleanValue;
import uk.co.jemos.podam.common.PodamStringValue;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateRequestVO implements Serializable {
    @PodamStringValue(strValue = "Email")
    private String templateType;

    @PodamStringValue(strValue = "Loan Approval")
    private String templateName;

    @PodamStringValue(strValue = "Dear customer, your loan application has been approved.")
    private String templateBody;

    @PodamStringValue(strValue = "Loan Application Update")
    private String templateSubject;

    @PodamStringValue(strValue = "ACTIVE")
    private String status;

    @PodamBooleanValue(boolValue = true)
    private Boolean evaluationRequired;
}