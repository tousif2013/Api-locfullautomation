package in.credable.automation.service.vo.section;

import lombok.Data;

@Data
public class UpdateSectionInstanceRequestVO {
    private Long id;
    private Long tabId;
    private String sectionJson;
}
