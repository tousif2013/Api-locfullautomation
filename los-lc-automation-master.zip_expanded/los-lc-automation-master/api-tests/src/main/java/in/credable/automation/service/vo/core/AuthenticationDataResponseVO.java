package in.credable.automation.service.vo.core;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationDataResponseVO {


    private Boolean twoFARequired;

    @JsonProperty("twoFactorAuthenticationId")
    private String twoFactorAuthenticationId;


    private String contactNumber;


    private String emailId;

    @JsonProperty("signUpRequired")
    private Boolean signUpRequired;


    @JsonProperty("token")
    private TokenVO tokenVO;

}
