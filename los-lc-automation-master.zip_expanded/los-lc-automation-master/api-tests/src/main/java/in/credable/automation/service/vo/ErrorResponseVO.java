package in.credable.automation.service.vo;

import lombok.Data;

@Data
public class ErrorResponseVO {
    private String errorCode;
    private String message;
    private String timestamp;
}
