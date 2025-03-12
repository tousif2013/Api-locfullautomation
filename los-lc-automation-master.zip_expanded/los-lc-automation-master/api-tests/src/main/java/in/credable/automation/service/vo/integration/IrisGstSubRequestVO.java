package in.credable.automation.service.vo.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.GSTNumberStrategy;
import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IrisGstSubRequestVO implements Serializable {

    @PodamStrategyValue(GSTNumberStrategy.class)
    private String gstin;

    @PodamStringValue(strValue = "GSTR1")
    private String returntype;

    private String fp;
    private String section;
    private String taxpayerName;
}
