package in.credable.automation.service.dataingestion;

import in.credable.automation.enums.StandardField;
import in.credable.automation.service.vo.dataingestion.*;

import java.util.List;
import java.util.Map;

import static in.credable.automation.service.dataingestion.PropertyValidators.*;

public final class DealerCustomPropertyMappings {
    public static final Map<StandardField, String> DEALER_FIELDS_MAPPINGS = DealerStandardFieldMappings.getDealerFieldsMappings();
    public static final CustomPropertyMappingVO DEALER_NAME = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.NAME.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.NAME))
            .fieldValueType(CustomPropertyFieldValueType.STRING)
            .propertyValidators(List.of(MANDATORY))
            .build();

    public static final CustomPropertyMappingVO DEALER_CODE = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.BORROWER_CODE.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.BORROWER_CODE))
            .fieldValueType(CustomPropertyFieldValueType.STRING)
            .propertyValidators(List.of(MANDATORY, REGEX.apply("^[a-zA-Z0-9]*$")))
            .build();

    public static final CustomPropertyMappingVO DEALER_PAN = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.PAN.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.PAN))
            .fieldValueType(CustomPropertyFieldValueType.STRING)
            .propertyValidators(List.of(MANDATORY, PAN, UNIQUE))
            .build();

    public static final CustomPropertyMappingVO ANCHOR_RECOMMENDED_LIMIT = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.RECOMMENDED_LIMIT.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.RECOMMENDED_LIMIT))
            .fieldValueType(CustomPropertyFieldValueType.INTEGER)
            .formatType(FormatType.AMOUNT)
            .propertyValidators(List.of(MANDATORY, PropertyValidatorVO.builder()
                    .type(PropertyValidatorType.RANGE)
                    .start(0.0)
                    .startInclusive(false)
                    .build()))
            .build();

    public static final CustomPropertyMappingVO DEALER_AUTHORIZED_PERSON_NAME = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.BORROWER_AUTHORIZED_PERSON_NAME.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.BORROWER_AUTHORIZED_PERSON_NAME))
            .fieldValueType(CustomPropertyFieldValueType.STRING)
            .propertyValidators(List.of(MANDATORY))
            .build();

    public static final CustomPropertyMappingVO DEALER_AUTHORIZED_PERSON_EMAIL = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.EMAIL.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.EMAIL))
            .fieldValueType(CustomPropertyFieldValueType.STRING)
            .propertyValidators(List.of(MANDATORY))
            .build();

    public static final CustomPropertyMappingVO DEALER_AUTHORIZED_PERSON_MOBILE = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.PHONE.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.PHONE))
            .fieldValueType(CustomPropertyFieldValueType.STRING)
            .propertyValidators(List.of(MANDATORY))
            .build();

    public static final CustomPropertyMappingVO DEALER_CREDIT_PERIOD = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.CREDIT_PERIOD.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.CREDIT_PERIOD))
            .fieldValueType(CustomPropertyFieldValueType.INTEGER)
            .propertyValidators(List.of(MANDATORY, PropertyValidatorVO.builder()
                    .type(PropertyValidatorType.RANGE)
                    .start(0)
                    .startInclusive(false)
                    .end(180)
                    .endInclusive(false)
                    .build()))
            .build();

    public static final CustomPropertyMappingVO DEALER_RELATIONSHIP_VINTAGE = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.RELATIONSHIP_VINTAGE.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.RELATIONSHIP_VINTAGE))
            .fieldValueType(CustomPropertyFieldValueType.INTEGER)
            .propertyValidators(List.of(MANDATORY, PropertyValidatorVO.builder()
                    .type(PropertyValidatorType.RANGE)
                    .start(0)
                    .startInclusive(false)
                    .end(60)
                    .endInclusive(false)
                    .build()))
            .build();

    public static final CustomPropertyMappingVO TOTAL_SALES_TO_DEALER_IN_LAST_FY = CustomPropertyMappingVO.builder()
            .standardFieldName(StandardField.TOTAL_SALES_LAST_FY.getStandardFieldName())
            .clientSpecificFieldName(DEALER_FIELDS_MAPPINGS.get(StandardField.TOTAL_SALES_LAST_FY))
            .fieldValueType(CustomPropertyFieldValueType.FLOAT)
            .propertyValidators(List.of(MANDATORY, PropertyValidatorVO.builder()
                    .type(PropertyValidatorType.RANGE)
                    .start(0)
                    .startInclusive(false)
                    .end(1000000000)
                    .endInclusive(false)
                    .build()))
            .build();
}
