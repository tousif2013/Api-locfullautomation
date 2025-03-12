package in.credable.automation.service.vo.platform;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryVO {
    private Long id;
    private String countryCodeIso2;
    private String countryName;
    private String dialingCode;
    private String currencyCodeIso3;
}
