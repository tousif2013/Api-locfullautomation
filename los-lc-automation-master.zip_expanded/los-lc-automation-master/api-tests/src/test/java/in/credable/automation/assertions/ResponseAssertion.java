package in.credable.automation.assertions;

import in.credable.automation.service.vo.ResponseWO;

import java.util.Objects;

public final class ResponseAssertion<T> extends CustomAssert<ResponseAssertion<T>, ResponseWO<T>> {
    ResponseAssertion(ResponseWO<T> actual) {
        super(actual, ResponseAssertion.class);
    }

    public ResponseAssertion<T> hasSameStatus(int status) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getStatus(), status, "status");
        return this;
    }

    public ResponseAssertion<T> hasSameCode(String expectedCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getCode(), expectedCode, "code");
        return this;
    }

    public ResponseAssertion<T> hasSameMessage(String expectedMessage) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getMessage(), expectedMessage, "message");
        return this;
    }

    public ResponseAssertion<T> timestampIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getTimestamp())) {
            failWithMessage("Timestamp should not be null in response body.");
        }
        return this;
    }

    public ResponseAssertion<T> dataIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getData())) {
            failWithMessage("Data should not be null in response body.");
        }
        return this;
    }

    public ResponseAssertion<T> dataIsNull() {
        isNotNull();
        if (Objects.nonNull(actual.getData())) {
            failWithMessage("Data should be null in response body.");
        }
        return this;
    }

    public ResponseAssertion<T> errorIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getError())) {
            failWithMessage("Error should not be null in response body.");
        }
        return this;
    }

    public ResponseAssertion<T> errorMessageIs(String message) {
        isNotNull();
        errorIsNotNull();
        failureWithActualExpectedForStringComparison(actual.getError().getMessage(), message, "Error message");
        return this;
    }

    public ResponseAssertion<T> errorIsNull() {
        isNotNull();
        super.propertyIsNull(actual.getError(), "Error");
        return this;
    }
}
