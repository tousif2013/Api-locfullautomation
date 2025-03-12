package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ValidateOTPVO {
    @JsonProperty("authenticationId")
    private String authenticationId;

    @JsonProperty("contactNumber")
    private String contactNumber;

    @JsonProperty("otp")
    private String otp;

    @JsonProperty("newPassword")
    private String newPassword;

}