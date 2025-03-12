package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.CashFreeService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.CashFreeVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author PriyankaSingh on 21/03/2024.
 */
public class CashFreeTest extends BaseTest {
    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("PENNY-DROP", "cashfreeService");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #556-Test to validate the Cashfree API Request and Response.")
    public void validateBankAccountVerificationResponse() {
        CashFreeVO cashFreeRequestVo = new CashFreeVO();
        cashFreeRequestVo.setBankAccount("987876765654");
        cashFreeRequestVo.setIfsc("HDFC0000128");
        cashFreeRequestVo.setName("Bhawana");
        cashFreeRequestVo.setPhone("8957557879");
        cashFreeRequestVo.setRemarks("na");
        ResponseWO<Object> responseWO = CashFreeService.BankAccountVerificationApiRequest(cashFreeRequestVo, vendorId)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseWO)
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
