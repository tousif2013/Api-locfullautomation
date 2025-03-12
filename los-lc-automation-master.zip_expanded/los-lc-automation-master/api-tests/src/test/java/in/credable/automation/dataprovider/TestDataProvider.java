package in.credable.automation.dataprovider;

import in.credable.automation.enums.StandardField;
import in.credable.automation.service.dataingestion.DealerStandardFieldMappings;
import in.credable.automation.service.vo.dataingestion.CustomPropertyMappingVO;
import in.credable.automation.utils.RandomDataGenerator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static in.credable.automation.enums.StandardField.*;
import static in.credable.automation.service.dataingestion.DealerCustomPropertyMappings.*;

public final class TestDataProvider {
    public static final Map<StandardField, String> DEALER_FIELDS_MAPPINGS = DealerStandardFieldMappings.getDealerFieldsMappings();

    private TestDataProvider() {
    }

    public static Map<String, Object> getBorrowerTestData() {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put(DEALER_FIELDS_MAPPINGS.get(NAME), RandomDataGenerator.generateRandomFirstName());
        data.put(DEALER_FIELDS_MAPPINGS.get(BORROWER_CODE), RandomDataGenerator.generateRandomString(10));
        data.put(DEALER_FIELDS_MAPPINGS.get(PAN), RandomDataGenerator.generateRandomPanNumber());
        data.put(DEALER_FIELDS_MAPPINGS.get(RECOMMENDED_LIMIT), 7000000);
        data.put(DEALER_FIELDS_MAPPINGS.get(BORROWER_AUTHORIZED_PERSON_NAME), RandomDataGenerator.generateRandomFirstName() + " " + RandomDataGenerator.generateRandomLastName());
        data.put(DEALER_FIELDS_MAPPINGS.get(EMAIL), RandomDataGenerator.generateRandomUniqueEmailId());
        data.put(DEALER_FIELDS_MAPPINGS.get(PHONE), RandomDataGenerator.generateRandomMobileNumber());
        data.put(DEALER_FIELDS_MAPPINGS.get(CREDIT_PERIOD), 30);
        data.put(DEALER_FIELDS_MAPPINGS.get(RELATIONSHIP_VINTAGE), 59);
        data.put(DEALER_FIELDS_MAPPINGS.get(TOTAL_SALES_LAST_FY), 500000);
        return data;
    }

    public static List<CustomPropertyMappingVO> getBorrowerCustomPropertyMappings() {
        return List.of(DEALER_NAME,
                DEALER_CODE,
                DEALER_PAN,
                ANCHOR_RECOMMENDED_LIMIT,
                DEALER_AUTHORIZED_PERSON_NAME,
                DEALER_AUTHORIZED_PERSON_EMAIL,
                DEALER_AUTHORIZED_PERSON_MOBILE,
                DEALER_CREDIT_PERIOD,
                DEALER_RELATIONSHIP_VINTAGE,
                TOTAL_SALES_TO_DEALER_IN_LAST_FY
        );
    }
}
