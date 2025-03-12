package in.credable.automation.testcases.integration;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.assertions.JsonPathAssertion;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.documentvault.DocumentVaultService;
import in.credable.automation.service.integration.CKYCService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.IdifyEKYCDownloadRequestVO;
import in.credable.automation.service.vo.integration.IdifyEKYCSearchRequestVO;
import in.credable.automation.service.vo.integration.VendorVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.*;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.LocalDate;

public class CKYCTest extends BaseTest {
    private static final String CKYC_NUMBER = RandomDataGenerator.generateRandomPanNumber();
    private static final String CKYC_IMAGE_PATH_PREFIX = "documents/Ckyc/" + CKYC_NUMBER + "/";
    private static final String CKYC_PERSONAL_DETAILS_JSON_PATH = "data.data[0].result.source_output.ckyc_personal_details";
    private String vendorId;
    private Response ckycDownloadResponse;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor("CKYC", "idfy");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #901 - Verify ckyc_search API for the IDFY CKYC")
    public void verifyCkycSearchAPI() {
        IdifyEKYCSearchRequestVO idifyEKYCSearchRequestVO = DataProviderUtil.manufacturePojo(IdifyEKYCSearchRequestVO.class);
        ResponseWO<Object> responseWO = CKYCService.ckycSearch(vendorId, idifyEKYCSearchRequestVO)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();
    }

    @Test(description = "Test #905 - Verify Download api of CKYC idfy")
    public void verifyCkycDownloadAPI() {
        IdifyEKYCDownloadRequestVO idifyEKYCDownloadRequestVO = DataProviderUtil.manufacturePojo(IdifyEKYCDownloadRequestVO.class);
        Response response = CKYCService.ckycDownload(vendorId, idifyEKYCDownloadRequestVO);
        ResponseWO<Object> responseWO = response.as(new TypeRef<>() {
        });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        assertCkycDownloadApiResponse(response);
    }

    @Test(description = "Test #906 - Verify the validation of ckyc search API")
    public void verifyMandatoryFieldsValidationsForCkycApi() {
        IdifyEKYCSearchRequestVO idifyEKYCSearchRequestVO = new IdifyEKYCSearchRequestVO();
        ResponseWO<Object> responseWO = CKYCService.ckycSearch(vendorId, idifyEKYCSearchRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();

        idifyEKYCSearchRequestVO.setCompanyCode(RandomDataGenerator.generateRandomCompanyName());
        idifyEKYCSearchRequestVO.setIdentityType("PAN");
        idifyEKYCSearchRequestVO.setIdNumber(CKYC_NUMBER);

        responseWO = CKYCService.ckycSearch(vendorId, idifyEKYCSearchRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();
    }

    @Test(description = "Test #907 - Verify the validation of ckyc download API")
    public void verifyMandatoryFieldsValidationsForCkycDownloadApi() {
        IdifyEKYCDownloadRequestVO idifyEKYCDownloadRequestVO = new IdifyEKYCDownloadRequestVO();
        ResponseWO<Object> responseWO = CKYCService.ckycDownload(vendorId, idifyEKYCDownloadRequestVO, StatusCode.BAD_REQUEST)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(400)
                .hasSameCode("INV001")
                .hasSameMessage(ApiMessageConstants.INVALID_INPUT)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull();

        idifyEKYCDownloadRequestVO.setCompanyCode(RandomDataGenerator.generateRandomString(5));
        idifyEKYCDownloadRequestVO.setCkycNumber(CKYC_NUMBER);
        idifyEKYCDownloadRequestVO.setDob(LocalDate.of(2000, 1, 1));

        ckycDownloadResponse = CKYCService.ckycDownload(vendorId, idifyEKYCDownloadRequestVO);
        responseWO = ckycDownloadResponse
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        assertCkycDownloadApiResponse(ckycDownloadResponse);
    }

    @Test(description = "Test #914 - Verify the document from the download API response get stored into the docvault"
            , dependsOnMethods = "verifyMandatoryFieldsValidationsForCkycDownloadApi")
    public void verifyDocumentFromDownloadApiResponseGetStoredIntoDocvault() {
        String ckycImage1Path = ckycDownloadResponse.body().jsonPath().getString(CKYC_PERSONAL_DETAILS_JSON_PATH +
                ".ckyc_image_details.ckyc_image_1.ckyc_image_data");
        assertCkycImagePath(ckycImage1Path);
        downloadCkycImageFromDocumentVault(ckycImage1Path);

        String ckycImage2Path = ckycDownloadResponse.body().jsonPath().getString(CKYC_PERSONAL_DETAILS_JSON_PATH +
                ".ckyc_image_details.ckyc_image_2.ckyc_image_data");
        assertCkycImagePath(ckycImage2Path);
        downloadCkycImageFromDocumentVault(ckycImage2Path);

        String ckycImage3Path = ckycDownloadResponse.body().jsonPath().getString(CKYC_PERSONAL_DETAILS_JSON_PATH +
                ".ckyc_image_details.ckyc_image_3.ckyc_image_data");
        assertCkycImagePath(ckycImage3Path);
        downloadCkycImageFromDocumentVault(ckycImage3Path);

        String ckycImage4Path = ckycDownloadResponse.body().jsonPath().getString(CKYC_PERSONAL_DETAILS_JSON_PATH +
                ".ckyc_image_details.ckyc_image_4.ckyc_image_data");
        assertCkycImagePath(ckycImage4Path);
        downloadCkycImageFromDocumentVault(ckycImage4Path);
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        TestHelper.changeVendorStatusToInactive(vendorId);
    }

    private void assertCkycDownloadApiResponse(Response response) {
        JsonPathAssertion.assertThat(response)
                .assertNotNull("data.data[0]")
                .assertNotNull("data.data[0].result")
                .assertNotNull("data.data[0].result.source_output")
                .assertNotNull(CKYC_PERSONAL_DETAILS_JSON_PATH)
                .assertNotNull(CKYC_PERSONAL_DETAILS_JSON_PATH + ".ckyc_image_details")
                .assertNotNull(CKYC_PERSONAL_DETAILS_JSON_PATH + ".ckyc_image_details.ckyc_image_1")
                .assertNotNull(CKYC_PERSONAL_DETAILS_JSON_PATH + ".ckyc_image_details.ckyc_image_2")
                .assertNotNull(CKYC_PERSONAL_DETAILS_JSON_PATH + ".ckyc_image_details.ckyc_image_3")
                .assertNotNull(CKYC_PERSONAL_DETAILS_JSON_PATH + ".ckyc_image_details.ckyc_image_4");
    }

    private void assertCkycImagePath(String ckycImagePath) {
        Assertions.assertThat(ckycImagePath)
                .isNotNull()
                .isNotEmpty()
                .startsWith(CKYC_IMAGE_PATH_PREFIX);
    }

    private void downloadCkycImageFromDocumentVault(String ckycImagePath) {
        String imageFileName = StringUtils.substringAfter(ckycImagePath, CKYC_IMAGE_PATH_PREFIX);
        Response response = DocumentVaultService.downloadDocument(ckycImagePath);
        String fileName = TestUtils.extractFileNameFromResponseHeader(response);
        Assertions.assertThat(fileName)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(imageFileName);
        File reportFile = TestUtils.convertResponseToTempFile(response, fileName);
        Assertions.assertThat(reportFile)
                .isNotNull()
                .isNotEmpty();
    }
}
