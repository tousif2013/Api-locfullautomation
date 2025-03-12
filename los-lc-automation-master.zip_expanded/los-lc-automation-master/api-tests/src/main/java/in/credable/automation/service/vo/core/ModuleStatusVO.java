package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleStatusVO {
    private ModuleExecutionStatusEnum status;
    private String moduleCode;
    private String error;
}
