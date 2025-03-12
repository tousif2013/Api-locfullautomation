package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.assertions.JsonPathAssertion;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.integration.FraudCheckService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.VendorType;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.service.vo.integration.WatchOutRequestVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.ApiMessageConstants;
import in.credable.automation.utils.DataProviderUtil;
import in.credable.automation.utils.RandomDataGenerator;
import in.credable.automation.utils.StatusCode;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * @author Prashant Rana
 */
public class WatchOutCheckTest extends BaseTest {
    private String vendorId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor(VendorType.WATCH_OUT.getValue(), "watchout");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #509 - Verify user should get the watchout investors information by passing the value, " +
            "Test #513 - Verify the Request and Response for the Watchout investors")
    public void verifyWatchoutInvestorsInformation() {
        WatchOutRequestVO watchOutRequestVO = new WatchOutRequestVO();
        ResponseWO<Object> responseWO = FraudCheckService.watchOutCheck(vendorId, watchOutRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();

        watchOutRequestVO = getWatchOutRequestVO();

        Response response = FraudCheckService.watchOutCheck(vendorId, watchOutRequestVO);
        responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        JsonPathAssertion.assertThat(response)
                .assertListIsNotEmpty("data.Table")
                .assertString("data.Table[0].Defaulter_Name", watchOutRequestVO.getDefaulterName())
                .assertString("data.Table[0].Defaulter_Type_Company_Person", watchOutRequestVO.getDefaulterType())
                .assertString("data.Table[0].PAN_CIN_DIN", "PAN:" + watchOutRequestVO.getPanCinDinNumber());
    }

    @Test(description = "Test #511 - Verify the client_id or source_id in header should be mandatory for get the information")
    public void verifyWatchoutInvestorsInformationWithoutClientId() {
        WatchOutRequestVO watchOutRequestVO = new WatchOutRequestVO();
        ResponseWO<Void> responseWO = FraudCheckService.watchOutCheck(vendorId,
                        watchOutRequestVO,
                        StatusCode.BAD_REQUEST,
                        Collections.emptyMap())
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV007")
                .hasSameMessage(ApiMessageConstants.CLIENT_ID_OR_SOURCE_ID_HEADER_NOT_PRESENT_IN_REQUEST)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();
    }

    @Test(description = "Test #532 - Verify the Mandatory field for the watchout request"
            , dataProvider = "watchOutRequestDataProvider")
    public void verifyWatchOutInvestorsInformationWithoutMandatoryField(WatchOutRequestVO watchOutRequestVO, String errorMessage) {
        ResponseWO<Void> responseWO = FraudCheckService.watchOutCheck(vendorId, watchOutRequestVO, StatusCode.BAD_REQUEST)
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

    @DataProvider
    private Object[][] watchOutRequestDataProvider() {
        WatchOutRequestVO watchOutRequestVO = getWatchOutRequestVO();
        WatchOutRequestVO defaulterNameEmpty = DataProviderUtil.clone(watchOutRequestVO);
        defaulterNameEmpty.setDefaulterName(null);
        WatchOutRequestVO defaulterTypeEmpty = DataProviderUtil.clone(watchOutRequestVO);
        defaulterTypeEmpty.setDefaulterType(null);
        WatchOutRequestVO searchTypeEmpty = DataProviderUtil.clone(watchOutRequestVO);
        searchTypeEmpty.setSearchType(null);
        WatchOutRequestVO panCinDinEmpty = DataProviderUtil.clone(watchOutRequestVO);
        panCinDinEmpty.setPanCinDinNumber(null);

        return new Object[][]{
                {defaulterNameEmpty, "defaulterName must not be blank"},
                {defaulterTypeEmpty, "defaulterType must not be blank"},
                {searchTypeEmpty, "searchType must not be blank"},
                {panCinDinEmpty, "panCinDinNumber must not be blank"}
        };
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }

    private WatchOutRequestVO getWatchOutRequestVO() {
        WatchOutRequestVO watchOutRequestVO = new WatchOutRequestVO();
        watchOutRequestVO.setDefaulterName("AVENUE SUPERMARTS LTD.");
        watchOutRequestVO.setDefaulterType("C");
        watchOutRequestVO.setSearchType("L");
        watchOutRequestVO.setPanCinDinNumber("AACCA8432H");
        watchOutRequestVO.setUniqueId(RandomDataGenerator.generateRandomNumber(5).intValue());
        watchOutRequestVO.setInternalRefId(RandomDataGenerator.generateRandomString(10));
        return watchOutRequestVO;
    }
}
