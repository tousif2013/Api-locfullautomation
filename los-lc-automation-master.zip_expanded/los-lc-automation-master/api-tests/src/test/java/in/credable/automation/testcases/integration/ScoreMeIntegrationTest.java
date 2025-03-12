package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.ScoreMeService;
import in.credable.automation.service.integration.VendorService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.SubmitITRRequestVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScoreMeIntegrationTest extends BaseTest {

    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        vendorRequestVO.setVendorType("ITR");
        vendorRequestVO.setApplicationServiceId("scoreMeService");
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

    @Test(description = "TC#506-Verify the functionality of 'Submit-ITR Request' API.")
    public void verifySubmitITRRequest() {
        SubmitITRRequestVO submitITRRequestVO = DataProviderUtil.manufacturePojo(SubmitITRRequestVO.class);
        submitITRRequestVO.setStartYear(null);
        submitITRRequestVO.setEndYear(null);
        ResponseWO<Object> responseVO = ScoreMeService.itrSubmitRequest(vendorId, StatusCode.OK, submitITRRequestVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNull();
    }

    @Test(description = "TC#512-Verify the functionality of 'ITR Report' API.")
    public void verifyGetITRReport() {
        ResponseWO<Object> responseVO = ScoreMeService.itrReport(vendorId, StatusCode.OK, "source1").as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        TestHelper.changeVendorStatusToInactive(vendorId);
    }
}
