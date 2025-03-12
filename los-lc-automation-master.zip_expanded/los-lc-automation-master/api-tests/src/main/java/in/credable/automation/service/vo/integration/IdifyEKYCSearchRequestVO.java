package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.credable.automation.attributestrategy.ClientCodeStrategy;
import in.credable.automation.attributestrategy.FirstNameStrategy;
import in.credable.automation.attributestrategy.LastNameStrategy;
import in.credable.automation.attributestrategy.RandomPANNoStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdifyEKYCSearchRequestVO {
    @JsonProperty("company_code")
    @PodamStrategyValue(value = ClientCodeStrategy.class)
    private String companyCode;

    @JsonProperty("identity_type")
    @PodamStringValue(strValue = "PAN")
    private String identityType;

    @JsonProperty("id_number")
    @PodamStrategyValue(value = RandomPANNoStrategy.class)
    private String idNumber;

    @JsonProperty("first_name")
    @PodamStrategyValue(value = FirstNameStrategy.class)
    private String firstName;

    @JsonProperty("middle_name")
    @PodamStrategyValue(value = FirstNameStrategy.class)
    private String middleName;

    @JsonProperty("last_name")
    @PodamStrategyValue(value = LastNameStrategy.class)
    private String lastName;

    @JsonProperty("gender")
    @PodamStringValue(strValue = "M")
    private String gender;
}
