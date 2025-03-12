package in.credable.automation.service.vo.core;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Jacksonized
@Getter
public class ModuleExecutionInfoRequestVO {
    private String moduleCode;
    private String moduleInstanceName;
    private String moduleInstanceID;
    private Integer clientId;
    private String userId;
    private Integer programId;
    private Map<String, Object> variables;
    private List<Map<String, Object>> moduleConfigData;
    private String loanApplicationId;
    private String transactionId;
    private String moduleExecutionId;
}
