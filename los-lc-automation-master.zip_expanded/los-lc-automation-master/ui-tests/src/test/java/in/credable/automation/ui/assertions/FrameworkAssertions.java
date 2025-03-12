package in.credable.automation.ui.assertions;

import in.credable.automation.ui.assertions.label.LabelKeyAssertion;
import in.credable.automation.ui.assertions.toastnotification.ToastNotificationAssertion;
import in.credable.automation.ui.pages.components.ToastNotification;

public final class FrameworkAssertions {
    public static ToastNotificationAssertion assertThat(ToastNotification toastNotification) {
        return new ToastNotificationAssertion(toastNotification);
    }

    public static LabelKeyAssertion assertThat(String actual) {
        return new LabelKeyAssertion(actual);
    }
}
