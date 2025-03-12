package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSOLoginVO implements Serializable {

    @PodamStrategyValue(value = FirstNameStrategy.class)
    private String firstName;

    @PodamStrategyValue(value = LastNameStrategy.class)
    private String lastName;

    @PodamStrategyValue(value = UniqueEmailStrategy.class)
    private String emailId;

    @PodamStrategyValue(value = MobileNumberStrategy.class)
    private String mobileNumber;

    @PodamStrategyValue(value = RandomPANNoStrategy.class)
    private String pan;
}
