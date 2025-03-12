package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmitITRRequestVO {
    private String sourceReferenceId;
    private String username;
    private String password;
    private String startYear;
    private String endYear;
    private String notificationUrl;
}
