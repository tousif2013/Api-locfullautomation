package in.credable.automation.service.vo.integration;

import in.credable.automation.attributestrategy.AddressStrategy;
import in.credable.automation.attributestrategy.FullNameStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
public class Director {
    @PodamStrategyValue(value = FullNameStrategy.class)
    private String name;

    @PodamStrategyValue(value = FullNameStrategy.class)
    private String fatherName;

    @PodamStrategyValue(value = AddressStrategy.class)
    private String address;
}
