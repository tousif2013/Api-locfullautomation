package in.credable.automation.service.vo.documentvault;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentUploadRequestVO {
    private String relativeStoragePath;
    private String transactionId;
    private String source;
    private String clientId;
    private Date timestamp;
    private String secret;
}
