package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.BusinessDetailService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.client.PANVerificationVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PanVerificationTest extends BaseTest {
    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("PAN", "idfyService");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #315 - Verify the functionality of Pan verification API in integration service.")
    public void verifyPan() {
        PANVerificationVO panVerificationVO = DataProviderUtil.manufacturePojo(PANVerificationVO.class);
        ResponseWO<Object> responseWO = BusinessDetailService.verifyPan(vendorId, panVerificationVO.getBusinessPAN())
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        TestHelper.changeVendorStatusToInactive(vendorId);
    }
}
