package in.credable.automation.ui.pages.adminapp.dealer;

import in.credable.automation.ui.language.LabelKey;
import lombok.Getter;

@Getter
public enum DealersTableColumn {
    DEALER_NAME(1, LabelKey.DASHBOARD_DEALER_HEADER_NAME),
    DEALER_CODE(2, LabelKey.DASHBOARD_DEALER_HEADER_BORROWER_CODE),
    PAN(3, LabelKey.DASHBOARD_DEALER_HEADER_PAN),
    CREATED_AT(4, LabelKey.DASHBOARD_DEALER_HEADER_CREATED_AT),
    CREATED_BY(5, LabelKey.DASHBOARD_DEALER_HEADER_CREATED_BY),
    CHECKED_AT(6, LabelKey.DASHBOARD_DEALER_HEADER_CHECKED_AT),
    CHECKED_BY(7, LabelKey.DASHBOARD_DEALER_HEADER_CHECKED_BY),
    STATUS(8, LabelKey.DASHBOARD_DEALER_HEADER_STATUS),
    ACTION(9, LabelKey.DASHBOARD_DEALER_HEADER_ACTION);

    private final int columnIndex;
    private final String labelKey;

    DealersTableColumn(int columnIndex, String labelKey) {
        this.columnIndex = columnIndex;
        this.labelKey = labelKey;
    }
}
