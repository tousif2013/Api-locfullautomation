package in.credable.automation.service.vo.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import in.credable.automation.attributestrategy.FirstNameStrategy;
import in.credable.automation.attributestrategy.LastNameStrategy;
import in.credable.automation.attributestrategy.UniqueEmailStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;


@Data
public class SignUpVO {
    @JsonProperty("userId")
    private String userId;

    @PodamStrategyValue(value = LastNameStrategy.class)
    private String firstName;

    @PodamStrategyValue(value = FirstNameStrategy.class)
    private String lastName;

    @PodamStrategyValue(value = UniqueEmailStrategy.class)
    private String emailId;

}