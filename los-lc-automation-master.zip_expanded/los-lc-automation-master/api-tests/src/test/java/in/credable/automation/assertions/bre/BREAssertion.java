package in.credable.automation.assertions.bre;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.bre.BreVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

public final class BREAssertion<T> extends CustomAssert<BREAssertion<T>, BreVO<T>> {
    BREAssertion(BreVO<T> actual) {
        super(actual, BREAssertion.class);
    }

    public BREAssertion<T> responseCodeisNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getResponseCode())) {
            failWithMessage("Response code should not be null.");
        }
        return this;
    }

    public BREAssertion<T> hasSameMessage(String expectedMessage) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getMessage(), expectedMessage, "Message");
        return this;
    }

    public BREAssertion<T> timeStampIsNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getTimestamp())) {
            failWithMessage("Timestamp should not be null.");
        }
        return this;
    }

    public BREAssertion<T> dataIsNotNull() {
        isNotNull();

        if (Objects.isNull(actual.getData())) {
            failWithMessage("Data should not be null.");
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public BREAssertion<T> dataIsNotEmptyList() {
        isNotNull();
        if (actual.getData() instanceof List) {
            List<T> dataList = (List<T>) actual.getData();
            if (CollectionUtils.isEmpty(dataList)) {
                failWithMessage("Data should not be empty.");
            }
        } else {
            failWithMessage("Data is not a list.");
        }
        return this;
    }

    public BREAssertion<T> hasSameResponseCode(String statusCode) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getResponseCode(), statusCode, "responseCode");
        return this;
    }

}
