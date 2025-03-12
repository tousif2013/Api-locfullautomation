package in.credable.automation.service.vo.core;


import lombok.Data;

import java.util.Date;

@Data
public class TemplateFileDownloadRequestVO {
    private Long programId;
    private DataIngestionFileFormatEnum fileFormat;
    private Date timestamp;
}
