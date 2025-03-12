package in.credable.automation.assertions;

import in.credable.automation.service.vo.ErrorResponseVO;

public final class ErrorAssertion extends CustomAssert<ErrorAssertion, ErrorResponseVO> {
    ErrorAssertion(ErrorResponseVO actual) {
        super(actual, ErrorAssertion.class);
    }

    public ErrorAssertion hasSameErrorCode(String expectedErrorCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getErrorCode(), expectedErrorCode, "Error code");
        return this;
    }

    public ErrorAssertion hasSameErrorMessage(String expectedErrorMessage) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getMessage(), expectedErrorMessage, "Error Message");
        return this;
    }

    public ErrorAssertion containsErrorMessage(String expectedErrorMessage) {
        isNotNull();
        if (!actual.getMessage().contains(expectedErrorMessage)) {
            failWithMessage("Different error message is displaying.");
        }
        return this;
    }

    public ErrorAssertion timestampIsNotNull() {
        isNotNull();
        super.propertyIsNotNull(actual.getTimestamp(), "timestamp");
        return this;
    }

}
