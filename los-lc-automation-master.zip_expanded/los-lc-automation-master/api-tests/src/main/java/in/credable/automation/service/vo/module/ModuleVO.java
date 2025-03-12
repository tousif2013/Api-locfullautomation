package in.credable.automation.service.vo.module;

import lombok.Data;

@Data
public class ModuleVO {
    private Long id;
    private ModuleCode moduleCode;
    private String moduleName;
    private String moduleType;
    private String moduleClass;
    private Long countryId;
    private String moduleJson;
    private Boolean frequentlyUsed;
}
