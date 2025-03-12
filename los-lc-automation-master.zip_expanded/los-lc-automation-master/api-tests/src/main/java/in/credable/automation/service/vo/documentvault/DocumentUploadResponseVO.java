package in.credable.automation.service.vo.documentvault;

import lombok.Data;

import java.util.Date;

@Data
public class DocumentUploadResponseVO {
    private String responseCode;
    private String message;
    private Date timestamp;
    private String providerFileLocation;
    private String uploadTransactionStatus;
    private String reason;
}
