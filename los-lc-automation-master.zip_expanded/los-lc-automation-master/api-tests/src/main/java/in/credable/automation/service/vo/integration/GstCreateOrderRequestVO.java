package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.RandomPANNoStrategy;
import in.credable.automation.attributestrategy.UrlStrategy;
import in.credable.automation.attributestrategy.UuidStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamCollection;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GstCreateOrderRequestVO implements Serializable {
    @PodamStrategyValue(value = UuidStrategy.class)
    private String sourceReferenceId;

    @PodamStrategyValue(value = RandomPANNoStrategy.class)
    private String pan;

    @PodamCollection(nbrElements = 1)
    private List<IrisGstSubRequestVO> subRequests;

    @PodamStrategyValue(value = UrlStrategy.class)
    private String notificationUrl;
}
