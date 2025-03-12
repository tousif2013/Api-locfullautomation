package in.credable.automation.assertions.documentvault;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.documentvault.DocumentUploadResponseVO;

import java.util.Objects;

public final class DocumentUploadResponseAssertion extends CustomAssert<DocumentUploadResponseAssertion, DocumentUploadResponseVO> {
    DocumentUploadResponseAssertion(DocumentUploadResponseVO actual) {
        super(actual, DocumentUploadResponseAssertion.class);
    }

    public DocumentUploadResponseAssertion hasSameResponseCode(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getResponseCode(), expected, "Response Code");
        return this;
    }

    public DocumentUploadResponseAssertion hasSameMessage(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getMessage(), expected, "Message");
        return this;
    }

    public DocumentUploadResponseAssertion timestampIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getTimestamp())) {
            failWithMessage("Timestamp is null");
        }
        return this;
    }

    public DocumentUploadResponseAssertion hasSameProviderFileLocation(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getProviderFileLocation(), expected, "Provider File Location");
        return this;
    }
}
