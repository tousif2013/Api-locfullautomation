package in.credable.automation.enums;

import lombok.Getter;

@Getter
public enum StandardField {
    NAME("name"),
    BORROWER_CODE("borrower_code"),
    PAN("pan"),
    RECOMMENDED_LIMIT("recommendedLimit"),
    BORROWER_AUTHORIZED_PERSON_NAME("borrower_authorized_person_name"),
    EMAIL("email"),
    PHONE("phone"),
    CREDIT_PERIOD("creditPeriod"),
    RELATIONSHIP_VINTAGE("relationshipVintage"),
    TOTAL_SALES_LAST_FY("totalSalesLastFY");

    private final String standardFieldName;

    StandardField(String standardFieldName) {
        this.standardFieldName = standardFieldName;
    }
}
