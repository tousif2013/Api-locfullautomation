package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LoginWithPasswordVO {

    private String username;

    private String password;
    private String oldPassword;
    private String newPassword;
}
