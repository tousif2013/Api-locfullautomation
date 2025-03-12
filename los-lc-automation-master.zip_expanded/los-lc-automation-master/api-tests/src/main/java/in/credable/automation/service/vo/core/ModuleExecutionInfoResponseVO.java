package in.credable.automation.service.vo.core;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleExecutionInfoResponseVO {
    private String loanApplicationId;
    private String moduleCode;
    private String moduleInstanceId;
    private String processInstanceId;
    private ModuleExecutionStatusEnum moduleExecutionStatus;
    private Map<String, Object> executionResult;
    private String error;
    private String errorCode;
    private long executionTime;
}
