package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStringValue;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientFieldMappingVO {
    @PodamExclude
    private Long id;

    @PodamStringValue(strValue = "Dealer Name")
    private String clientFieldName;

    @PodamStringValue(strValue = "name")
    private String standardFieldName;

    private String languageKey;

    @PodamExclude
    private Long clientId;
}
