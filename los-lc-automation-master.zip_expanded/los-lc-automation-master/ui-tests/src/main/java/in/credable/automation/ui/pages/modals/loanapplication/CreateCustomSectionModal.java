package in.credable.automation.ui.pages.modals.loanapplication;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.pages.modals.BaseModal;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

public class CreateCustomSectionModal extends BaseModal {
    private static final SelenideElement SECTION_NAME_INPUT = $(byCssSelector("#programCode"));
    private static final SelenideElement CREATE_BUTTON = $(byCssSelector(".submit_button"));

    public void createSection(String sectionName) {
        enterSectionName(sectionName);
        clickCreateButton();
    }

    private void enterSectionName(String sectionName) {
        SECTION_NAME_INPUT.setValue(sectionName);
    }

    private void clickCreateButton() {
        CREATE_BUTTON.click();
    }
}
