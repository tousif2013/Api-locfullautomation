package in.credable.automation.service.vo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.*;
import lombok.Data;
import uk.co.jemos.podam.common.*;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO implements Serializable {

    @PodamExclude
    private String userId;

    @PodamExclude
    private String username;

    @PodamStrategyValue(FirstNameStrategy.class)
    private String firstName;

    @PodamStrategyValue(LastNameStrategy.class)
    private String lastName;

    @PodamStrategyValue(UniqueEmailStrategy.class)
    private String emailId;

    @PodamStrategyValue(MobileNumberStrategy.class)
    private String mobileNumber;

    @PodamStringValue(strValue = "+91")
    private String countryCode;

    @PodamExclude
    private String defaultPassword;

    @PodamBooleanValue
    private Boolean twoFaEnabled;

    @PodamExclude
    private List<String> roles;

    @PodamExclude
    private List<UserAttributeVO> userAttributes;

    @PodamBooleanValue(boolValue = true)
    private Boolean active;
}
