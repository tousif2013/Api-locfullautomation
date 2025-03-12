package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanUpdationRequestVO {

    private String moduleCode;

    private LoanApplicationStatusEnum status;

    private String moduleInstanceName;

    private String errorCode;

    private String errorDetails;

    private String flowableProcessInstanceId;

    private String updatedBy;

    private String checkedBy;

}
