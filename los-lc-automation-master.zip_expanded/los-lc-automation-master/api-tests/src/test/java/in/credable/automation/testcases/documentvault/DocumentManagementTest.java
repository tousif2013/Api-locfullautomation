package in.credable.automation.testcases.documentvault;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.commons.utils.FileUtil;
import in.credable.automation.service.documentvault.DocumentVaultService;
import in.credable.automation.service.vo.documentvault.DocumentUploadRequestVO;
import in.credable.automation.service.vo.documentvault.DocumentUploadResponseVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.TestUtils;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

import java.io.File;

public class DocumentManagementTest extends BaseTest {
    private static final String UPLOAD_FILENAME = RandomDataGenerator.getUuid() + ".xlsx";
    private String uploadedDocumentStoragePath;

    @Test(description = "Test #3 - Verify if upload API is working correctly when triggered from postman.")
    public void verifyUploadingDocument() {
        File file = FileUtil.createTempFile(UPLOAD_FILENAME);
        DocumentUploadRequestVO documentUploadRequestVO = new DocumentUploadRequestVO();
        documentUploadRequestVO.setRelativeStoragePath("doc-vault/automation");

        DocumentUploadResponseVO documentUploadResponseVO = DocumentVaultService.uploadDocument(documentUploadRequestVO, file)
                .as(DocumentUploadResponseVO.class);

        FrameworkAssertions.assertThat(documentUploadResponseVO)
                .hasSameResponseCode("CRED_1")
                .hasSameMessage("Document uploaded successfully")
                .hasSameProviderFileLocation("documents/" + documentUploadRequestVO.getRelativeStoragePath() + "/" + UPLOAD_FILENAME)
                .timestampIsNotNull();

        this.uploadedDocumentStoragePath = documentUploadResponseVO.getProviderFileLocation();
    }

    @Test(description = "Test #4 - Verify if download API is working correctly for file when triggered from postman."
            , dependsOnMethods = "verifyUploadingDocument"
            , priority = 1)
    public void verifyDocumentDownload() {
        Response response = DocumentVaultService.downloadDocument(this.uploadedDocumentStoragePath);
        String fileNameWithExtension = TestUtils.extractFileNameFromResponseHeader(response);
        Assertions.assertThat(fileNameWithExtension)
                .isEqualTo(UPLOAD_FILENAME);

        File file = TestUtils.convertResponseToTempFile(response, fileNameWithExtension);

        Assertions.assertThat(file)
                .isNotNull();
    }
}
