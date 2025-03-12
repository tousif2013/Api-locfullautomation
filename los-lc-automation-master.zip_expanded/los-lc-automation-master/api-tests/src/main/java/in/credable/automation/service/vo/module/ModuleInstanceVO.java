package in.credable.automation.service.vo.module;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleInstanceVO implements Serializable {
    private Long id;
    private Long moduleId;
    private String moduleInstanceName;
    private String moduleInstanceUiKey;
    private String moduleInstanceUiJson;
    private String moduleInstanceDataKey;
    private String moduleInstanceDataJson;
    private ModuleCode moduleCode;
    private ModuleVO moduleVO;
    private Long index;
    private String taskType;
}
