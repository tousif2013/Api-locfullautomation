package in.credable.automation.service.vo.dataingestion;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class DataIngestionUploadDataResponseVO {
    private String responseCode;
    private String message;
    private Date timestamp;
    private List<Map<String, Object>> rowData;
    private String validationSummaryPath;
}
