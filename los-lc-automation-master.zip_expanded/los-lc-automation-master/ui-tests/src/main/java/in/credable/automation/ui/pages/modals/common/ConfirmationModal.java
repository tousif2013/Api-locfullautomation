package in.credable.automation.ui.pages.modals.common;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.pages.modals.BaseModal;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

public class ConfirmationModal extends BaseModal {
    private static final SelenideElement CONFIRM_BUTTON = $(byCssSelector(".confirmation_yes"));

    public void selectYes() {
        CONFIRM_BUTTON.shouldBe(visible).click();
    }
}
