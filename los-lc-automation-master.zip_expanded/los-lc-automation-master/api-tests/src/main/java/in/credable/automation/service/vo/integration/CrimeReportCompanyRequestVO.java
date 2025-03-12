package in.credable.automation.service.vo.integration;

import in.credable.automation.attributestrategy.AddressStrategy;
import in.credable.automation.attributestrategy.CompanyNameStrategy;
import in.credable.automation.attributestrategy.GSTNumberStrategy;
import in.credable.automation.attributestrategy.UuidStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.jemos.podam.common.PodamCollection;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class CrimeReportCompanyRequestVO extends CrimeReportBaseRequest {
    @PodamStrategyValue(value = CompanyNameStrategy.class)
    private String companyName;

    @PodamStrategyValue(value = AddressStrategy.class)
    private String companyAddress;

    private String companyType;

    @PodamCollection(nbrElements = 1)
    private List<Director> directors;

    @PodamStrategyValue(value = GSTNumberStrategy.class)
    private String gstNumber;

    private String cinNumber;

    @PodamStrategyValue(UuidStrategy.class)
    private String sourceReferenceId;

    private String internalRefId;
}
