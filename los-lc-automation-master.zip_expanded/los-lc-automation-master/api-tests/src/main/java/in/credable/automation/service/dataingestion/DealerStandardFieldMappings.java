package in.credable.automation.service.dataingestion;

import in.credable.automation.enums.StandardField;

import java.util.EnumMap;
import java.util.Map;

import static in.credable.automation.enums.StandardField.*;

public final class DealerStandardFieldMappings {
    private static final Map<StandardField, String> DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS = new EnumMap<>(StandardField.class);

    static {
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(NAME, "Dealer Name");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(BORROWER_CODE, "Dealer code");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(PAN, "Dealer PAN No.");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(RECOMMENDED_LIMIT, "Anchor Recommended Limit (in INR)");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(BORROWER_AUTHORIZED_PERSON_NAME, "Dealer Authorized Person Name");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(EMAIL, "Dealer Authorized Person Email");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(PHONE, "Dealer Authorized Person Mobile No.");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(CREDIT_PERIOD, "Dealer Credit Period (in days)");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(RELATIONSHIP_VINTAGE, "Dealer Relationship Vintage (in months)");
        DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS.put(TOTAL_SALES_LAST_FY, "Total Sales to Dealer (last FY) (in INR)");
    }

    private DealerStandardFieldMappings() {
    }

    public static Map<StandardField, String> getDealerFieldsMappings() {
        return DEALER_STANDARD_CUSTOM_FIELD_MAPPINGS;
    }
}
