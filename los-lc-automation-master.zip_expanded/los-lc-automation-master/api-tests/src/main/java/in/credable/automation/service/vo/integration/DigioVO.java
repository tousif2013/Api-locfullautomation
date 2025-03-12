package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.SourceReferenceStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamCollection;
import uk.co.jemos.podam.common.PodamStrategyValue;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DigioVO {

    private String fileUrl;

    @PodamStrategyValue(SourceReferenceStrategy.class)
    private String sourceReferenceId;

    private String notificationUrl;

    @PodamCollection(nbrElements = 1)
    private List<SignersVO> signers;
    private String profileId;
}

