package in.credable.automation.assertions.core;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.UploadDataResponseVO;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public final class UploadDataResponseAssertion extends CustomAssert<UploadDataResponseAssertion, UploadDataResponseVO> {
    UploadDataResponseAssertion(UploadDataResponseVO uploadDataResponseVO) {
        super(uploadDataResponseVO, UploadDataResponseAssertion.class);
    }

    public UploadDataResponseAssertion hasSameResponseCode(String expectedResponseCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getResponseCode(), expectedResponseCode, "Response code");
        return this;
    }

    public UploadDataResponseAssertion hasSameResponseMessage(String expectedMessage) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getMessage(), expectedMessage, "Message");
        return this;
    }

    public UploadDataResponseAssertion timeStampIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getTimestamp())) {
            failWithMessage("Timestamp should not be null.");
        }
        return this;
    }

    public UploadDataResponseAssertion validationSummaryPathIsNotNull() {
        isNotNull();
        if (StringUtils.isBlank(actual.getValidationSummaryPath())) {
            failWithMessage("Validation summary path should not be null.");
        }
        return this;
    }
}
