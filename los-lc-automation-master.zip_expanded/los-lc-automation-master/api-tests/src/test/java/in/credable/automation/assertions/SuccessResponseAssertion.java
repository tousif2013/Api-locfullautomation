package in.credable.automation.assertions;

import in.credable.automation.service.vo.SuccessResponseVO;

public final class SuccessResponseAssertion extends CustomAssert<SuccessResponseAssertion, SuccessResponseVO> {
    SuccessResponseAssertion(SuccessResponseVO actual) {
        super(actual, SuccessResponseAssertion.class);
    }

    public SuccessResponseAssertion hasSameResponseCode(String expectedResponseCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getResponseCode(),
                expectedResponseCode,
                "Response Code");
        return this;
    }

    public SuccessResponseAssertion hasSameResponseMessage(String expectedResponseMessage) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getResponseMessage(),
                expectedResponseMessage,
                "Response message");
        return this;
    }

}
