package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.config.IntegrationDataConfig;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.BusinessDetailService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.GstCreateOrderRequestVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GstinIntegrationTest extends BaseTest {
    private static final IntegrationDataConfig INTEGRATION_DATA_CONFIG = ConfigFactory.getIntegrationDataConfig();
    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("PAN/GST", "irisService");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #319 - Verify the functionality of gstin List API in integration service")
    public void verifyFetchingGstinForGivenPAN() {
        ResponseWO<Object> responseWO = BusinessDetailService.listGstin(vendorId, INTEGRATION_DATA_CONFIG.getPanToGstinPanNo())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #323 - Verify the functionality of gstin detail API in integration service")
    public void verifyFetchingGstinDetails() {
        ResponseWO<Object> responseWO = BusinessDetailService.getGstinDetails(vendorId, INTEGRATION_DATA_CONFIG.getGstinNo())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .dataIsNotNull()
                .timestampIsNotNull();
    }

    @Test(description = "Test #325 - Verify the functionality of gstin detail API in integration service with different /Invalid gstin.")
    public void verifyFetchingGstinDetailsWithInvalidGstin() {
        ResponseWO<Object> responseWO = BusinessDetailService.getGstinDetails(vendorId,
                        RandomDataGenerator.generateRandomString(10),
                        StatusCode.BAD_REQUEST)
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
                .isEqualTo(ApiMessageConstants.INVALID_GSTIN);
    }

    @Test(description = "Test #563 - Iris | Verify the Create Order API with invalid inputs"
            , dataProvider = "getGstCreateOrderRequestVO")
    public void verifyCreatingGstOrderWithInvalidInput(GstCreateOrderRequestVO gstCreateOrderRequestVO, String errorMessage) {
        ResponseWO<Void> responseWO = BusinessDetailService.createGstOrder(vendorId, gstCreateOrderRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs(errorMessage);
    }

    @Test(description = "Test #566 - Iris | Verify confirm Order API with invalid inputs")
    public void verifyConfirmingGstOrderWithInvalidInput() {
        // 1. Send confirm order request without sourceReferenceId
        ResponseWO<Void> responseWO = BusinessDetailService.confirmGstOrder(vendorId, null, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs("Invalid Source Reference Id");

        // 2. Send confirm order request with invalid sourceReferenceId
        responseWO = BusinessDetailService.confirmGstOrder(vendorId, RandomDataGenerator.generateRandomString(5), StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs("Invalid Source Reference Id");
    }

    @Test(description = "Test #567 - Iris | Verify Report API with invalid inputs")
    public void verifyGstReportWithInvalidInput() {
        // 1. Send report request without sourceReferenceId
        ResponseWO<Void> responseWO = BusinessDetailService.getGstReport(vendorId, null, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs("Invalid Source Reference Id");

        // 2. Send report request with invalid sourceReferenceId
        responseWO = BusinessDetailService.getGstReport(vendorId, RandomDataGenerator.generateRandomString(5), StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs("Invalid Source Reference Id");
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }

    @DataProvider
    private Object[][] getGstCreateOrderRequestVO() {
        GstCreateOrderRequestVO emptySourceReferenceId = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptySourceReferenceId.setSourceReferenceId(null);

        GstCreateOrderRequestVO emptyPanNumber = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptyPanNumber.setPan(null);

        GstCreateOrderRequestVO emptySubRequests = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptySubRequests.setSubRequests(null);

        GstCreateOrderRequestVO emptyNotificationUrl = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptyNotificationUrl.setNotificationUrl(null);

        GstCreateOrderRequestVO emptyGstin = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptyGstin.getSubRequests().getFirst().setGstin(null);

        GstCreateOrderRequestVO emptyReturnType = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptyReturnType.getSubRequests().getFirst().setReturntype(null);

        GstCreateOrderRequestVO emptyFp = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptyFp.getSubRequests().getFirst().setFp(null);

        GstCreateOrderRequestVO emptySection = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptySection.getSubRequests().getFirst().setSection(null);

        GstCreateOrderRequestVO emptyTaxpayerName = DataProviderUtil.manufacturePojo(GstCreateOrderRequestVO.class);
        emptyTaxpayerName.getSubRequests().getFirst().setTaxpayerName(null);

        return new Object[][]{
                {emptySourceReferenceId, "sourceReferenceId must not be blank"},
                {emptyPanNumber, "pan must not be blank"},
                {emptySubRequests, "subRequests must not be empty"},
                {emptyNotificationUrl, "notificationUrl must not be blank"},
                {emptyGstin, "gstin must not be blank"},
                {emptyReturnType, "returnType must not be blank"},
                {emptyFp, "fp must not be blank"},
                {emptySection, "section must not be blank"},
                {emptyTaxpayerName, "taxpayerName must not be blank"}
        };
    }
}
