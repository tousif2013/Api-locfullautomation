package in.credable.automation.service.vo.client;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

@Data
public class ClientThemeVO {

    @PodamExclude
    private Long id;

    @PodamExclude
    private Long clientId;

    @PodamStringValue(strValue = "#411f1f")
    private String header;

    @PodamStringValue(strValue = "#3b3519")
    private String footer;

    @PodamStringValue(strValue = "#3b3519")
    private String button;

    @PodamStringValue(strValue = "#3b3519")
    private String brand;
}
