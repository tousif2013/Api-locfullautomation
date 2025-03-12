package in.credable.automation.service.vo.integration;

import in.credable.automation.attributestrategy.AddressStrategy;
import in.credable.automation.attributestrategy.FullNameStrategy;
import in.credable.automation.attributestrategy.RandomPANNoStrategy;
import in.credable.automation.attributestrategy.UuidStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.jemos.podam.common.PodamStrategyValue;

@EqualsAndHashCode(callSuper = true)
@Data
public class CrimeReportIndividualRequestVO extends CrimeReportBaseRequest {
    @PodamStrategyValue(value = FullNameStrategy.class)
    private String name;

    @PodamStrategyValue(value = FullNameStrategy.class)
    private String fatherName;

    @PodamStrategyValue(value = AddressStrategy.class)
    private String address;
    private String address2;
    private String dob;

    @PodamStrategyValue(value = RandomPANNoStrategy.class)
    private String panNumber;

    @PodamStrategyValue(UuidStrategy.class)
    private String sourceReferenceId;
}
