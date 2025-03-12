package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.EmailStrategy;
import in.credable.automation.attributestrategy.MobileNumberStrategy;
import in.credable.automation.attributestrategy.UserNameStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignersVO {
    @PodamStrategyValue(UserNameStrategy.class)
    private String name;

    @PodamStrategyValue(EmailStrategy.class)
    private String email;

    @PodamStrategyValue(MobileNumberStrategy.class)
    private String contactNumber;
}
