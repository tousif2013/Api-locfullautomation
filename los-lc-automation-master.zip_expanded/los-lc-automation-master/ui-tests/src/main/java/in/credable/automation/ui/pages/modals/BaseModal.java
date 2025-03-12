package in.credable.automation.ui.pages.modals;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.utils.SelenideUtils;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

public abstract class BaseModal {
    protected static final SelenideElement POPUP_CONTAINER = $(byCssSelector(".popup__container"));
    protected static final SelenideElement POPUP_TITLE = POPUP_CONTAINER.$(byCssSelector(".header_title"));
    protected static final SelenideElement POPUP_CLOSE_BUTTON = POPUP_CONTAINER.$(byCssSelector(".close_icon"));

    public boolean isModalOpened() {
        return SelenideUtils.isElementVisible(POPUP_CONTAINER);
    }

    public void waitForModal() {
        POPUP_CONTAINER.shouldBe(appear);
    }

    public String getModalTitle() {
        return POPUP_TITLE.getText();
    }

    public void closeModal() {
        POPUP_CLOSE_BUTTON.click();
    }
}
