package in.credable.automation.ui.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum CurrencyCode {
    INR("₹"),
    USD("$"),
    EUR("€");

    private final String currencySymbol;

    CurrencyCode(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public static CurrencyCode decode(String currencyCodeIso3) {
        return Arrays.stream(CurrencyCode.values())
                .filter(currencyCode -> StringUtils.equals(currencyCode.name(), currencyCodeIso3))
                .findFirst()
                .orElseThrow();
    }
}
