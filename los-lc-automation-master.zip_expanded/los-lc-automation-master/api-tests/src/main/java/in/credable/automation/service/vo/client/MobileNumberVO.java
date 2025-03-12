package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import in.credable.automation.attributestrategy.MobileNumberStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import uk.co.jemos.podam.common.PodamStrategyValue;
import uk.co.jemos.podam.common.PodamStringValue;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class MobileNumberVO {
    @PodamStringValue(strValue = "+91")
    private String dialingCode;

    @PodamStrategyValue(MobileNumberStrategy.class)
    private String number;

    @JsonIgnore
    public String getNumberWithDialingCode() {
        return this.dialingCode + " " + this.number;
    }

    public MobileNumberVO(String mobileNumberWithDialingCode, char separator) {
        String[] parts = StringUtils.split(mobileNumberWithDialingCode, separator);
        if (parts.length == 1) {
            number = parts[0];
        } else if (parts.length == 2) {
            dialingCode = parts[0];
            number = parts[1];
        }
    }
}
