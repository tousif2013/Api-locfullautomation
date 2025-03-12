package in.credable.automation.assertions.notification;

import in.credable.automation.service.vo.notification.TemplateVO;

public final class NotificationAssertionsFactory {
    private NotificationAssertionsFactory() {
    }

    public static TemplateAssertion createTemplateAssertion(TemplateVO actual) {
        return new TemplateAssertion(actual);
    }
}
