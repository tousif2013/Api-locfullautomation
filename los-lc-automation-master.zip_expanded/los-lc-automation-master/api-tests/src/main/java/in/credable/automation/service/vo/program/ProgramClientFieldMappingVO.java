package in.credable.automation.service.vo.program;

import in.credable.automation.service.vo.client.ClientFieldMappingVO;
import lombok.Data;

@Data
public class ProgramClientFieldMappingVO {

    private Long id;

    private Long programId;

    private ClientFieldMappingVO clientFieldMappingVO;

    private boolean isMandatory;
}
