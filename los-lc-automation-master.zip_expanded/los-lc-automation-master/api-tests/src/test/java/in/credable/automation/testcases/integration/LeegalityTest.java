package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.assertions.JsonPathAssertion;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.DigioService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.DigioVO;
import in.credable.automation.service.vo.integration.SignersVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.StatusCode;
import in.credable.automation.utils.TestConstants;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;


public class LeegalityTest extends BaseTest {
    private String sourceReferenceId;
    private String signURL;
    private DigioVO digiovo;
    private String vendorId;
    private String deletedSourceReferenceId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("E-Sign", "leegalityESign");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #568 - Leegality | Verify the 'Create an eSigning Request API'.")
    public void verifyEsignSubmitRequest() {
        digiovo = DataProviderUtil.manufacturePojo(DigioVO.class);
        digiovo.setProfileId(TestConstants.LEEGALITY_PROFILE_ID);
        digiovo.setFileUrl(TestConstants.LEEGALITY_FILE_URL);
        Response response = DigioService.submitEsignRequest(digiovo, vendorId);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
        sourceReferenceId = digiovo.getSourceReferenceId();
        signURL = response.jsonPath().get("data.data.invitees[0].signUrl");
    }

    @Test(description = "Test #571 - Leegality | Verify the 'Get Document Details' API."
            , dependsOnMethods = "verifyEsignSubmitRequest"
            , priority = 1)
    public void verifyEsignDetailsRequest() {
        Response response = DigioService.eSignDocumentDetail(sourceReferenceId, vendorId);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        SignersVO signersVO = digiovo.getSigners().getFirst();
        JsonPathAssertion.assertThat(response).assertString("data.data.workflowId", digiovo.getProfileId())
                .assertString("data.data.irn", digiovo.getSourceReferenceId())
                .assertString("data.data.invitations[0].name", signersVO.getName())
                .assertString("data.data.invitations[0].email", signersVO.getEmail())
                .assertString("data.data.invitations[0].phone", signersVO.getContactNumber());
    }

    @Test(description = "Test #569 - Leegality | Verify the 'Transaction status'."
            , dependsOnMethods = "verifyEsignSubmitRequest"
            , priority = 2)
    public void verifyTransactionStatus() {
        Response response = DigioService.eSignTransactionStatus(sourceReferenceId, vendorId);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        JsonPathAssertion.assertThat(response)
                .assertString("data.status", "1");
    }

    @Test(description = "Test #643 -Leegality | Verify the signed download API "
            , dependsOnMethods = "verifyEsignSubmitRequest"
            , priority = 3)
    public void verifyEsignedDownload() {
        Response response = DigioService.downloadEsignDocument(sourceReferenceId, vendorId);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        JsonPathAssertion.assertThat(response)
                .assertNotNull("data.docVaultUrl");
    }

    @Test(description = "Test #557 - Leegality | Verify the Download Signed API for invalid sourceReferenceId"
            , priority = 4)
    public void verifyDownloadingEsignedDocumentWithInvalidSourceReferenceId() {
        ResponseWO<Object> responseWO = DigioService.downloadEsignDocument("", vendorId, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .errorIsNotNull()
                .dataIsNull();

        Assertions.assertThat(responseWO.getError().getMessage())
                .as(() -> "Error message is not expected")
                .isEqualTo(ApiMessageConstants.INVALID_SOURCE_REFERENCE_ID);
    }

    @Test(description = "Test #574 - Leegality | Verify the 'Resend Notification' API"
            , dependsOnMethods = "verifyEsignSubmitRequest"
            , priority = 5)
    public void verifyResendNotification() {
        Response response = DigioService.resendEsignNotification(sourceReferenceId, vendorId, List.of(signURL));
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        SignersVO signersVO = digiovo.getSigners().getFirst();
        JsonPathAssertion.assertThat(response)
                .assertString("data.data.invitations[0].message", ApiMessageConstants.LEEGALITY_RESEND_NOTIFICATION)
                .assertString("data.data.invitations[0].name", signersVO.getName())
                .assertString("data.data.invitations[0].email", signersVO.getEmail())
                .assertString("data.data.invitations[0].phone", signersVO.getContactNumber());
    }

    @Test(description = "Test #573 - Leegality | Verify the 'Cancel a invitation' API."
            , dependsOnMethods = "verifyEsignSubmitRequest"
            , priority = 6)
    public void verifyEsignCancelRequest() {
        Response response = DigioService.cancelEsignRequest(sourceReferenceId, vendorId, signURL);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        JsonPathAssertion.assertThat(response)
                .assertString("data.messages[0].message", ApiMessageConstants.LEEGALITY_CANCEL_REQUEST_MESSAGE);
    }

    @Test(description = "Test #570 - Leegality | Verify the 'Delete Invitation' API "
            , priority = 7)
    public void verifyDeleteEsignInvitation() {
        DigioVO digioVO = DataProviderUtil.manufacturePojo(DigioVO.class);
        digioVO.setProfileId(TestConstants.LEEGALITY_PROFILE_ID);
        digioVO.setFileUrl(TestConstants.LEEGALITY_FILE_URL);

        ResponseWO<Object> submitEsignResponse = DigioService.submitEsignRequest(digioVO, vendorId)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(submitEsignResponse)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
        deletedSourceReferenceId = digioVO.getSourceReferenceId();
        Response response = DigioService.deleteEsignInvitation(deletedSourceReferenceId, vendorId);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

        JsonPathAssertion.assertThat(response)
                .assertString("data.messages[0].message", ApiMessageConstants.LEEGALITY_DELETE_INVITATION_MESSAGE);
    }

    @Test(description = "Test #541 - Leegality | Verify the Delete Invitation API for non-existing invitation"
            , dependsOnMethods = "verifyDeleteEsignInvitation"
            , priority = 8)
    public void verifyDeletingNonExistingEsignInvitation() {
        // 1. Delete the invitation with invalid "sourceReferenceId"
        ResponseWO<Void> responseWO = DigioService.deleteEsignInvitation("", vendorId, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();

        Assertions.assertThat(responseWO.getError().getMessage())
                .as(() -> "Error message is not expected")
                .isEqualTo(ApiMessageConstants.INVALID_SOURCE_REFERENCE_ID);

        // 2. Delete again with already deleted sourceReferenceId
        responseWO = DigioService.deleteEsignInvitation(deletedSourceReferenceId, vendorId, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();

        Assertions.assertThat(responseWO.getError().getMessage())
                .as(() -> "Error message is not expected")
                .isEqualTo(ApiMessageConstants.CANNOT_DELETE_THIS_DOCUMENT_DOCUMENT_NOT_PRESENT);
    }

    @AfterClass(alwaysRun = true)
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }
}
