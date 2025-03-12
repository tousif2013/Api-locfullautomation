package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import uk.co.jemos.podam.common.PodamLongValue;
import uk.co.jemos.podam.common.PodamStringValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LanguageVO {
    @PodamLongValue(numValue = "1")
    private Long id;

    @PodamStringValue(strValue = "EN")
    private String languageCodeIso2;

    @PodamStringValue(strValue = "English")
    private String languageName;
}
