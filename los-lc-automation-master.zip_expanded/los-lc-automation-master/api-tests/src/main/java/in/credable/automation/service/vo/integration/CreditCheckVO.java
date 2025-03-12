package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCheckVO {

    @PodamStrategyValue(value = FullNameStrategy.class)
    private String name;
    @PodamStrategyValue(value = RandomPANNoStrategy.class)
    private String pan;
    @PodamStringValue(strValue = "2024-03-18")
    private String dob;
    @PodamStringValue(strValue = "MALE")
    private String gender;
    @PodamStrategyValue(value = MobileNumberStrategy.class)
    private String phone;
    @PodamStrategyValue(AddressStrategy.class)
    private String addressLine1;
    @PodamStrategyValue(CityNameStrategy.class)
    private String city;
    @PodamStrategyValue(StateNameStrategy.class)
    private String state;
    @PodamStrategyValue(PinCodeStrategy.class)
    private String pinCode;
    private int enquiryAmount;
    private String enquiryPurpose;

}
