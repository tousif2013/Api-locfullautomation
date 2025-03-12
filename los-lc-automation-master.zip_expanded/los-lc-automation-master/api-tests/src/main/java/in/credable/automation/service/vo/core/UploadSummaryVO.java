package in.credable.automation.service.vo.core;

import lombok.Data;

@Data
public class UploadSummaryVO {
    private Integer total;
    private Integer uploaded;
    private Integer failed;
}
