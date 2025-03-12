package in.credable.automation.service.vo.section;

import lombok.Data;

@Data
public class SectionVO {
    private Long id;
    private String sectionCode;
    private String sectionName;
    private String sectionType;
    private Boolean isCustom;
    private Boolean isEditable;
    private String sectionJson;
}
