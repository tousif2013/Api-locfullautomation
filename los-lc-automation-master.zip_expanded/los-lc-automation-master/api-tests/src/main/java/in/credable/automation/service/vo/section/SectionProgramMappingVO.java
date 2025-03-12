package in.credable.automation.service.vo.section;

import lombok.Data;

@Data
public class SectionProgramMappingVO {
    private Long id;
    private SectionVO section;
    private Long programId;
    private Integer sequenceNo;
    private Boolean isActive;
    private SectionTabVO sectionTab;
    private String sectionJson;
}
