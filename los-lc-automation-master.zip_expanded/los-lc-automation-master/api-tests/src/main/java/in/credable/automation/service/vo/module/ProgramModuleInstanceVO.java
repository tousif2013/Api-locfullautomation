package in.credable.automation.service.vo.module;

import lombok.Data;

@Data
public class ProgramModuleInstanceVO {
    private Long id;
    private Long programId;
    private Long moduleInstanceId;
    private ModuleInstanceVO moduleInstanceVO;
}

