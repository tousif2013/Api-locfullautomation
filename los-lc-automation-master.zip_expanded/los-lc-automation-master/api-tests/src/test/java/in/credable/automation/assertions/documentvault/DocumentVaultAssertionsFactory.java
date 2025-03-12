package in.credable.automation.assertions.documentvault;

import in.credable.automation.service.vo.documentvault.DocumentUploadResponseVO;

public final class DocumentVaultAssertionsFactory {
    private DocumentVaultAssertionsFactory() {
    }

    public static DocumentUploadResponseAssertion createDocumentUploadResponseAssertion(DocumentUploadResponseVO actual) {
        return new DocumentUploadResponseAssertion(actual);
    }
}
