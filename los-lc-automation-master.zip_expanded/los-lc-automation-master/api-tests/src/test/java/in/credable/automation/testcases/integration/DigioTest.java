package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.DigioService;
import in.credable.automation.service.integration.VendorService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.DigioVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.TestConstants;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DigioTest extends BaseTest {
    private String sourceReferenceId;
    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        vendorRequestVO.setVendorType("For esigning");
        vendorRequestVO.setApplicationServiceId("digioService");
        ResponseWO<VendorVO> responseWO = VendorService.addVendor(vendorRequestVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
        vendorId = responseWO.getData().getId();
    }

    @Test(description = "Test #585 - Hit the submit-request API with the given test data and validate the response", priority = 0)
    public void verifyEsignSubmitRequestResponse() {
        DigioVO digiovo = DataProviderUtil.manufacturePojo(DigioVO.class);
        digiovo.setFileUrl(TestConstants.DIGIO_FILE_URL);
        digiovo.setNotificationUrl(TestConstants.NOTIFICATION_URL);
        ResponseWO<Object> responseWo = DigioService.submitEsignRequest(digiovo, vendorId).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWo)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
        sourceReferenceId = digiovo.getSourceReferenceId();
    }


    @Test(description = "Test #586 - Hit the Cancel API and validate the Request and Response", priority = 1)
    public void verifyEsignCancelRequestResponse() {
        verifyEsignSubmitRequestResponse();
        ResponseWO<Object> responseWo = DigioService.cancelEsignRequest(sourceReferenceId, vendorId).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWo)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();

    }

    @Test(description = "Test #587 - Hit the details API and validate the Request and Response", dependsOnMethods = "verifyEsignSubmitRequestResponse", priority = 2)
    public void verifyEsignDetailsRequestResponse() {
        ResponseWO<Object> responseWo = DigioService.eSignDocumentDetail(sourceReferenceId, vendorId).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWo)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #588 - Test to validate the downloads API Request and Response.", dependsOnMethods = "verifyEsignSubmitRequestResponse", priority = 3)
    public void verifyEsignDownloadsRequestResponse() {
        ResponseWO<Object> responseWo = DigioService.downloadEsignDocument(sourceReferenceId, vendorId).as(new TypeRef<>() {
        });
        ;
        FrameworkAssertions.assertThat(responseWo)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        TestHelper.changeVendorStatusToInactive(vendorId);
    }
}
