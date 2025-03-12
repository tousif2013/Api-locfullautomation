package in.credable.automation.testcases.integration;

import com.fasterxml.jackson.databind.JsonNode;
import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.commons.utils.SerializationUtil;
import in.credable.automation.helper.TestHelper;
import in.credable.automation.service.documentvault.DocumentVaultService;
import in.credable.automation.service.integration.FraudCheckService;
import in.credable.automation.service.vo.ResponseWO;
import in.credable.automation.service.vo.integration.*;
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

public class CrimeCheckTest extends BaseTest {
    private String vendorId;
    private String companyReportSourceReferenceId;
    private String individualReportSourceReferenceId;

    @BeforeClass
    public void createVendor() {
        VendorVO vendor = TestHelper.createVendor(VendorType.CRIME_CHECK.getValue(), "crimeCheckService");
        vendorId = vendor.getId();
    }

    @Test(description = "Test #514 - CC | Verify the Add report API to retrieve the report of the company & Individual")
    public void verifyCrimeCheckReportSubmission() {
        // 1. Submit crime-check report for company
        CrimeReportCompanyRequestVO crimeReportCompanyRequestVO = DataProviderUtil.manufacturePojo(CrimeReportCompanyRequestVO.class);
        ResponseWO<CrimeCheckResponseVO> responseWO = FraudCheckService.submitCrimeCheckRequestForCompany(vendorId, crimeReportCompanyRequestVO)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        Assertions.assertThat(responseWO.getData().getStatus())
                .as(() -> "Status should be OK")
                .isEqualTo("OK");
        Assertions.assertThat(responseWO.getData().getRequestId())
                .as(() -> "Request Id should be present")
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(responseWO.getData().getRequestTime())
                .as(() -> "Request time should be present")
                .isNotNull()
                .isNotEmpty();

        companyReportSourceReferenceId = crimeReportCompanyRequestVO.getSourceReferenceId();

        // 2. Submit crime-check report for individual
        CrimeReportIndividualRequestVO crimeReportIndividualRequestVO = DataProviderUtil.manufacturePojo(CrimeReportIndividualRequestVO.class);
        responseWO = FraudCheckService.submitCrimeCheckRequestForIndividual(vendorId, crimeReportIndividualRequestVO)
                .as(new TypeRef<>() {
                });
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        Assertions.assertThat(responseWO.getData().getStatus())
                .as(() -> "Status should be OK")
                .isEqualTo("OK");
        Assertions.assertThat(responseWO.getData().getRequestId())
                .as(() -> "Request Id should be present")
                .isNotNull()
                .isNotEmpty();
        Assertions.assertThat(responseWO.getData().getRequestTime())
                .as(() -> "Request time should be present")
                .isNotNull()
                .isNotEmpty();

        individualReportSourceReferenceId = crimeReportIndividualRequestVO.getSourceReferenceId();
    }

    @Test(description = "Test #516 - CC | Verify user is able to download the JSON report for the Crime Watch using request sourceReferenceId, " +
            "Test #533 - CC| Verify that from the JSON response of Crime case have 3 tags that have document link, that document should download and store in s3 bucket"
            , dependsOnMethods = "verifyCrimeCheckReportSubmission")
    public void verifyCrimeCheckJSONReportDownload() {
        // 1. Download company crime check json report for existing sourceReferenceId

        ResponseWO<CrimeCheckJsonReportResponseVO> responseWO = FraudCheckService.getCrimeCheckJsonReport(vendorId, companyReportSourceReferenceId)
                .as(new TypeRef<>() {
                });

        assertJsonReport(responseWO);

        // 2. Try to download json report for non-existing sourceReferenceId
        ResponseWO<Void> responseWO1 = FraudCheckService.getCrimeCheckJsonReport(vendorId,
                        RandomDataGenerator.generateRandomString(5),
                        StatusCode.INTERNAL_SERVER_ERROR)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO1)
                .hasSameStatus(500)
                .hasSameCode("INTEG002")
                .hasSameMessage(ApiMessageConstants.SOMETHING_WENT_WRONG)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs(ApiMessageConstants.REPORT_IS_NOT_PRESENT_FOR_THIS_SOURCE_ID);

        // 3. Download individual crime check json report for existing sourceReferenceId
        responseWO = FraudCheckService.getCrimeCheckJsonReport(vendorId, individualReportSourceReferenceId)
                .as(new TypeRef<>() {
                });

        assertJsonReport(responseWO);
    }

    @Test(description = "Test #517 - CC | Verify user is able to download the PDF for the Company and Individual through requestID"
            , dependsOnMethods = "verifyCrimeCheckReportSubmission")
    public void verifyCrimeCheckPDFReportDownload() {
        // 1. Download company crime check pdf report for existing sourceReferenceId
        ResponseWO<CrimeCheckReportVO> responseWO = FraudCheckService.getCrimeCheckPdfReport(vendorId, companyReportSourceReferenceId)
                .as(new TypeRef<>() {
                });

        assertPdfReport(responseWO);

        // 2. Try to download pdf report for non-existing sourceReferenceId
        ResponseWO<Void> responseWO1 = FraudCheckService.getCrimeCheckPdfReport(vendorId,
                        RandomDataGenerator.generateRandomString(5),
                        StatusCode.INTERNAL_SERVER_ERROR)
                .as(new TypeRef<>() {
                });

        FrameworkAssertions.assertThat(responseWO1)
                .hasSameStatus(500)
                .hasSameCode("INTEG002")
                .hasSameMessage(ApiMessageConstants.SOMETHING_WENT_WRONG)
                .timestampIsNotNull()
                .dataIsNull()
                .errorIsNotNull()
                .errorMessageIs(ApiMessageConstants.REPORT_IS_NOT_PRESENT_FOR_THIS_SOURCE_ID);


        // 3. Download individual crime check pdf report for existing sourceReferenceId
        responseWO = FraudCheckService.getCrimeCheckPdfReport(vendorId, individualReportSourceReferenceId)
                .as(new TypeRef<>() {
                });
        assertPdfReport(responseWO);
    }

    private void assertPdfReport(ResponseWO<CrimeCheckReportVO> responseWO) {
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        Assertions.assertThat(responseWO.getData())
                .isNotNull();
        Assertions.assertThat(responseWO.getData().getDocVaultUrl())
                .isNotEmpty();

        Response response = DocumentVaultService.downloadDocument(responseWO.getData().getDocVaultUrl());
        String fileName = TestUtils.extractFileNameFromResponseHeader(response);
        Assertions.assertThat(fileName)
                .isNotNull()
                .isNotEmpty()
                .endsWith(".pdf");
        File reportFile = TestUtils.convertResponseToTempFile(response, fileName);
        Assertions.assertThat(reportFile)
                .isNotNull()
                .isNotEmpty();
    }

    private void assertJsonReport(ResponseWO<CrimeCheckJsonReportResponseVO> responseWO) {
        FrameworkAssertions.assertThat(responseWO)
                .hasSameStatus(200)
                .hasSameCode("INTEG001")
                .hasSameMessage(ApiMessageConstants.REQUEST_PROCESSED_SUCCESSFULLY)
                .timestampIsNotNull()
                .dataIsNotNull()
                .errorIsNull();

        JsonNode jsonNode = SerializationUtil.deserialize(responseWO.getData().getResult(), JsonNode.class);
        Assertions.assertThat(jsonNode)
                .isNotNull();
        Assertions.assertThat(jsonNode.get("status"))
                .isNotNull();
        Assertions.assertThat(jsonNode.get("status").asText())
                .isNotNull()
                .isNotEmpty()
                .isEqualTo("OK");
        Assertions.assertThat(jsonNode.at("/searchTerm"))
                .isNotNull();
        JsonNode caseDetails = jsonNode.at("/caseDetails");
        Assertions.assertThat(caseDetails)
                .isNotNull();
        Assertions.assertThat(caseDetails.isArray())
                .isTrue();
        for (JsonNode caseDetail : caseDetails) {
            Assertions.assertThat(caseDetail.has("firLink"))
                    .isTrue();
            Assertions.assertThat(caseDetail.has("judgementLink"))
                    .isTrue();
            Assertions.assertThat(caseDetail.has("firLink"))
                    .isTrue();
            Assertions.assertThat(caseDetail.has("caseLink"))
                    .isTrue();
        }
    }

    @AfterClass
    public void changeVendorStatusToInactive() {
        if (StringUtils.isNotBlank(vendorId)) {
            TestHelper.changeVendorStatusToInactive(vendorId);
        }
    }
}
