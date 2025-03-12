package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.IntegrationDataConfig;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.Probe42Service;
import in.credable.automation.service.integration.VendorService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Probe42IntegrationTest extends BaseTest {
    private static final String PAN_QUERY_PARAMNAME = "pan";
    private static final String LLPIN_QUERY_PARAMNAME = "llpin";
    private static final String CIN_QUERY_PARAMNAME = "cin";
    private static final IntegrationDataConfig INTEGRATION_DATA_CONFIG = ConfigFactory.getIntegrationDataConfig();

    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendorRequestVO = DataProviderUtil.manufacturePojo(VendorVO.class);
        vendorRequestVO.setVendorType("MCA");
        vendorRequestVO.setApplicationServiceId("probe42Service");
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


    //https://apidocumentation.probe42.in/documentation/sandbox_1_3.html#/Base%20API/get_probe_pro_sandbox_companies__CinOrPan__base_details
    @Test(description = "TC#466-Verify the functionality of 'Comprehensive detail for LLP' API  with PAN.")
    public void llpComprehensiveDetailWithPAN() {
        ResponseWO<Object> responseVO = Probe42Service.getLLPComprehensiveDetail(vendorId, PAN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getLLPPAN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#581-Verify the functionality of 'Comprehensive detail for LLP' API  with LLPIN.")
    public void llpComprehensiveDetailWIthLLPIN() {
        ResponseWO<Object> responseVO = Probe42Service.getLLPComprehensiveDetail(vendorId, LLPIN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getLLPLLPIN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#469-Verify the functionality of 'Basic detail for LLP' API  with PAN.")
    public void llpBasicDetailWithPAN() {
        ResponseWO<Object> responseVO = Probe42Service.getLLPBasicDetail(vendorId, PAN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getLLPPAN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#582-Verify the functionality of 'Basic detail for LLP' API  with LLPIN.")
    public void llpBasicDetailWithLLPIN() {
        ResponseWO<Object> responseVO = Probe42Service.getLLPBasicDetail(vendorId, LLPIN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getLLPLLPIN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#473-Verify the functionality of 'Comprehensive detail for Company' API  with PAN.")
    public void companyComprehensiveDetailWithPAN() {
        ResponseWO<Object> responseVO = Probe42Service.getCompanyComprehensiveDetail(vendorId, PAN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getCompanyPAN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#584-Verify the functionality of 'Comprehensive detail for Company' API  with CIN.")
    public void companyComprehensiveDetailWithCIN() {
        ResponseWO<Object> responseVO = Probe42Service.getCompanyComprehensiveDetail(vendorId, CIN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getCompanyCIN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#583- Verify the functionality of 'Basic detail for Company' API  with CIN.")
    public void companyBasicDetailWithCIN() {
        ResponseWO<Object> responseVO = Probe42Service.getCompanyBasicDetail(vendorId, CIN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getCompanyCIN()).as(new TypeRef<>() {
        });
        FrameworkAssertions.assertThat(responseVO)
                .hasSameStatus(200)
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull();
    }

    @Test(description = "TC#476- Verify the functionality of 'Basic detail for Company' API  with PAN.")
    public void companyBasicDetailWithPAN() {
        ResponseWO<Object> responseVO = Probe42Service.getCompanyBasicDetail(vendorId, PAN_QUERY_PARAMNAME, INTEGRATION_DATA_CONFIG.getCompanyPAN()).as(new TypeRef<>() {
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
