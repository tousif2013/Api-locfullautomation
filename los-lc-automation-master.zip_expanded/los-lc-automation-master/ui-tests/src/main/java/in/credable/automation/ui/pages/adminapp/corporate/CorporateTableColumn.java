package in.credable.automation.ui.pages.adminapp.corporate;

import lombok.Getter;

@Getter
public enum CorporateTableColumn {
    CORPORATE_ID(1),
    CORPORATE_NAME(2),
    CORPORATE_SUBSEGMENT(3),
    AUTHORISED_PERSON_NAME(4),
    AUTHORISED_PHONE_NUMBER(5),
    APPROVAL_STATUS(6),
    ACTION(7);

    private final int columnIndex;

    CorporateTableColumn(int columnIndex) {
        this.columnIndex = columnIndex;
    }

}
