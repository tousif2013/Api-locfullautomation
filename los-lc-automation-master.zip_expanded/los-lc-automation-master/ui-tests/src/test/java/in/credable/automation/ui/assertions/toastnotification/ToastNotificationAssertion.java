package in.credable.automation.ui.assertions.toastnotification;

import in.credable.automation.ui.pages.components.ToastNotification;
import org.assertj.core.api.Assertions;

public final class ToastNotificationAssertion {
    private final ToastNotification toastNotification;

    public ToastNotificationAssertion(ToastNotification toastNotification) {
        this.toastNotification = toastNotification;
    }

    public ToastNotificationAssertion isSuccessNotificationDisplayed() {
        Assertions.assertThat(toastNotification.isSuccessNotificationDisplayed())
                .as(() -> "Success notification is not displayed")
                .isTrue();
        return this;
    }

    public ToastNotificationAssertion notificationMessageIs(String expectedMessage) {
        Assertions.assertThat(toastNotification.getNotificationMessage())
                .as(() -> "Notification message is not as expected")
                .isEqualTo(expectedMessage);
        return this;
    }

    public ToastNotificationAssertion isErrorNotificationDisplayed() {
        Assertions.assertThat(toastNotification.isErrorNotificationDisplayed())
                .as(() -> "Error notification is not displayed")
                .isTrue();
        return this;
    }
}
