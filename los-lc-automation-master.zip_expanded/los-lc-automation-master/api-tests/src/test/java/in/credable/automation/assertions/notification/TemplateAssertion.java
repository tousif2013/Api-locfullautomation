package in.credable.automation.assertions.notification;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.notification.TemplateRequestVO;
import in.credable.automation.service.vo.notification.TemplateVO;
import org.assertj.core.api.Assertions;

import java.util.List;

public final class TemplateAssertion extends CustomAssert<TemplateAssertion, TemplateVO> {
    TemplateAssertion(TemplateVO templateVO) {
        super(templateVO, TemplateAssertion.class);
    }

    public TemplateAssertion vendorIdIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getVendorId(), expected, "vendorId");
        return this;
    }

    public TemplateAssertion clientIdIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getClientId(), expected, "clientId");
        return this;
    }

    public TemplateAssertion templateCodeIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getTemplateCode(), expected, "templateCode");
        return this;
    }

    public TemplateAssertion templateRequestListIsSameAs(List<TemplateRequestVO> expected) {
        isNotNull();
        Assertions.assertThat(actual.getTemplateRequestList())
                .as(() -> "Template request list is not matching with expected value")
                .isEqualTo(expected);
        return this;
    }
}
