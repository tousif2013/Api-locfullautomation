package in.credable.automation.ui.pages.components;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.utils.SelenideUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public final class ToastNotification {
    private static final SelenideElement ALERT_BOX = $("div.alert_box");
    private static final SelenideElement SUCCESS_ALERT = $("div.alert_box.success");
    private static final SelenideElement ERROR_ALERT = $("div.alert_box.fail");
    private static final SelenideElement ALERT_MESSAGE = ALERT_BOX.$(".alert_message");
    private static final SelenideElement ALERT_CLOSE = ALERT_BOX.$(".alert_close");

    public boolean isSuccessNotificationDisplayed() {
        return SelenideUtils.isElementVisible(SUCCESS_ALERT);
    }

    public boolean isErrorNotificationDisplayed() {
        return SelenideUtils.isElementVisible(ERROR_ALERT);
    }

    public String getNotificationMessage() {
        return ALERT_MESSAGE.shouldBe(visible).getText();
    }

    public void closeNotification() {
        if (ALERT_BOX.isDisplayed()) {
            ALERT_CLOSE.click();
            ALERT_BOX.shouldNotBe(visible);
        }
    }
}
