package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import in.credable.automation.attributestrategy.ClientCodeStrategy;
import in.credable.automation.attributestrategy.RandomBooleanValueStrategy;
import in.credable.automation.attributestrategy.RandomPANNoStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdifyEKYCDownloadRequestVO {
    @JsonProperty("company_code")
    @PodamStrategyValue(value = ClientCodeStrategy.class)
    private String companyCode;

    @JsonProperty("ckyc_number")
    @PodamStrategyValue(value = RandomPANNoStrategy.class)
    private String ckycNumber;

    @JsonProperty("dob")
    private LocalDate dob;

    @JsonProperty("relation_details")
    @PodamStrategyValue(RandomBooleanValueStrategy.class)
    private Boolean relationDetails;

    @JsonProperty("ckyc_verification_details")
    @PodamStrategyValue(RandomBooleanValueStrategy.class)
    private Boolean ckycVerificationDetails;
}
