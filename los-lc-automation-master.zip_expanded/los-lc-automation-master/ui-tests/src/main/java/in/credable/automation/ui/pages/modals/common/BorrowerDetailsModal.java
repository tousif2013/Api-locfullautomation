package in.credable.automation.ui.pages.modals.common;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.pages.modals.BaseModal;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class BorrowerDetailsModal extends BaseModal {
    private static final SelenideElement BORROWER_NAME = POPUP_CONTAINER.$(byCssSelector("div.company_name"));
    private static final SelenideElement CLOSE_BUTTON = $(byCssSelector(".close_dealer_view"));
    private static final SelenideElement BORROWER_DETAIL_BOX = POPUP_CONTAINER.$(byCssSelector("div.customer_detail_box"));
    private static final ElementsCollection BORROWER_DETAILS = BORROWER_DETAIL_BOX.$$(byCssSelector("div.customer_detail"));

    public String getBorrowerName() {
        String borrowerName = BORROWER_NAME.getText();
        log.info("Borrower Name: {}", borrowerName);
        return borrowerName;
    }

    @Override
    public void closeModal() {
        CLOSE_BUTTON.click();
        log.info("Closed borrower detail modal");
    }

    public Map<String, String> getBorrowerDetails() {
        Map<String, String> borrowerDetails = new HashMap<>();
        for (SelenideElement borrowerDetail : BORROWER_DETAILS) {
            String key = borrowerDetail.$(byCssSelector("div.customer_detail_title")).getText();
            String value = borrowerDetail.$(byCssSelector("div.customer_detail_value")).getText();
            borrowerDetails.put(key, value);
        }
        log.info("Borrower Details: {}", borrowerDetails);
        return borrowerDetails;
    }

}
