package in.credable.automation.service.vo.core;

import lombok.Data;

import java.util.Date;


@Data
public class UploadDataResponseVO {
    private String responseCode;
    private String message;
    private Date timestamp;
    private String validationSummaryPath;
    private UploadSummaryVO summary;
}
