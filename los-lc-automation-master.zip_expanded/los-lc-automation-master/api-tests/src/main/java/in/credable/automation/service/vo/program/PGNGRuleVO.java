package in.credable.automation.service.vo.program;

import in.credable.automation.attributestrategy.RandomBooleanValueStrategy;
import in.credable.automation.attributestrategy.UrlStrategy;
import in.credable.automation.attributestrategy.UuidStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
public class PGNGRuleVO {
    @PodamStrategyValue(UuidStrategy.class)
    private String id;

    @PodamExclude
    private String ruleName;

    @PodamExclude
    private String ruleTye;

    @PodamExclude
    private String version;

    @PodamStrategyValue(UrlStrategy.class)
    private String url;

    @PodamStrategyValue(RandomBooleanValueStrategy.class)
    private boolean async;

    @PodamStrategyValue(UrlStrategy.class)
    private String notificationUrl;
}
