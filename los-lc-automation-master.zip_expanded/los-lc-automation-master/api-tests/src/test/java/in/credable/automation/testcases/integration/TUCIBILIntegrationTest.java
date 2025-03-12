package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.TUCIBILService;
import in.credable.automation.service.integration.VendorService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.CreditCheckVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TUCIBILIntegrationTest extends BaseTest {
    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        vendorRequestVO.setVendorType("CREDIT-CHECK");
        vendorRequestVO.setApplicationServiceId("tuCibilService");
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

    @Test(description = "TC#302-Verify the functionality of 'Credit check' API in integration service.")
    public void verifyCreditCheckTUCIBIL() {
        CreditCheckVO creditCheckVO = DataProviderUtil.manufacturePojo(CreditCheckVO.class);
        ResponseWO<Object> responseWO = TUCIBILService.creditCheck(vendorId, creditCheckVO).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseWO)
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
