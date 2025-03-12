package in.credable.automation.testcases.core;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.commons.excel.ExcelReader;
import in.credable.automation.commons.excel.ExcelWriter;
import in.credable.automation.config.ConfigFactory;
import in.credable.automation.dataprovider.TestDataProvider;
import in.credable.automation.service.auth.AuthService;
import in.credable.automation.service.core.LosCoreService;
import in.credable.automation.service.documentvault.DocumentVaultService;
import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.ErrorResponseVO;
import in.credable.automation.service.vo.client.ClientFieldMappingVO;
import in.credable.automation.service.vo.core.DataIngestionFileFormatEnum;
import in.credable.automation.service.vo.core.FileUploadRequestDataVO;
import in.credable.automation.service.vo.core.TemplateFileDownloadRequestVO;
import in.credable.automation.service.vo.core.UploadDataResponseVO;
import in.credable.automation.service.vo.program.ProgramClientFieldMappingVO;
import in.credable.automation.testcases.BaseTest;
import in.credable.automation.utils.StatusCode;
import in.credable.automation.utils.TestUtils;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LosCoreTest extends BaseTest {
    private static final String TEMPLATE_EXCEL_FILE_NAME = "template.xlsx";
    private static final String UPLOAD_EXCEL_FILE_NAME = "upload.xlsx";
    private static final String TEMP_DIRECTORY = FileUtils.getTempDirectory().getAbsolutePath();
    private static final Long AUTOMATION_PROGRAM_ID = ConfigFactory.getEnvironmentConfig().getAutomationProgramId();
    private ExcelReader excelReader;
    private String validationSummaryFilePath;

    @SneakyThrows
    @Test(description = "Test #246 - Verify the functionality of download template API")
    public void verifyDownloadingTemplateExcelFile() {
        TemplateFileDownloadRequestVO templateFileDownloadRequestVO = new TemplateFileDownloadRequestVO();
        templateFileDownloadRequestVO.setProgramId(AUTOMATION_PROGRAM_ID);
        templateFileDownloadRequestVO.setFileFormat(DataIngestionFileFormatEnum.XLSX);
        templateFileDownloadRequestVO.setTimestamp(new Date());

        Response response = LosCoreService.downloadTemplate(templateFileDownloadRequestVO);
        String fileName = TestUtils.extractFileNameFromResponseHeader(response);

        Assertions.assertThat(fileName)
                .as(() -> "Downloaded template excel file name is not expected")
                .isEqualTo(TEMPLATE_EXCEL_FILE_NAME);

        File templateExcelFile = TestUtils.convertResponseToTempFile(response, TEMPLATE_EXCEL_FILE_NAME);

        this.excelReader = new ExcelReader(templateExcelFile, 0);
        List<String> headerRowCellData = this.excelReader.getHeaderRowCellData();

        List<String> clientFieldNames = ProgramService.getProgramClientFieldMappings(AUTOMATION_PROGRAM_ID)
                .<List<ProgramClientFieldMappingVO>>as(new TypeRef<>() {
                }).stream()
                .map(ProgramClientFieldMappingVO::getClientFieldMappingVO)
                .map(ClientFieldMappingVO::getClientFieldName)
                .toList();

        Assertions.assertThat(headerRowCellData)
                .as(() -> "Header row cell data is not expected")
                .containsExactlyInAnyOrderElementsOf(clientFieldNames);

    }

    @Test(description = "Test #247 - Verify the functionality of download template API with invalid input in request body"
            , dataProvider = "fileFormatProvider")
    public void verifyDownloadingTemplateExcelFileWithInvalidInput(DataIngestionFileFormatEnum dataIngestionFileFormatEnum) {
        TemplateFileDownloadRequestVO templateFileDownloadRequestVO = new TemplateFileDownloadRequestVO();
        templateFileDownloadRequestVO.setProgramId(AUTOMATION_PROGRAM_ID);
        templateFileDownloadRequestVO.setFileFormat(dataIngestionFileFormatEnum);
        templateFileDownloadRequestVO.setTimestamp(new Date());

        ErrorResponseVO errorResponseVO = LosCoreService.downloadTemplate(templateFileDownloadRequestVO,
                        StatusCode.BAD_REQUEST
                )
                .as(ErrorResponseVO.class);

        FrameworkAssertions.assertThat(errorResponseVO)
                .hasSameErrorCode("CRED_LOS_402")
                .hasSameErrorMessage("The value '%s' is not valid for fileFormat".formatted(dataIngestionFileFormatEnum))
                .timestampIsNotNull();
    }

    @Test(description = "Test #180 - Verify Successful Bulk Data Upload for Dealer, " +
            "Test #198 - Verify Successful Bulk Data Upload for Vendor"
            , dependsOnMethods = "verifyDownloadingTemplateExcelFile"
            , priority = 1)
    public void verifyUploadingBulkDataForBorrower() {
        // Generate access token
        String accessToken = AuthService.getAccessToken(ConfigFactory.getEnvironmentConfig().getAdminAppUserName());

        // Write Data to templateExcelFile
        Map<String, Object> borrowerTestData = TestDataProvider.getBorrowerTestData();
        ExcelWriter excelWriter = new ExcelWriter(this.excelReader);
        excelWriter.writeDataIntoNewRow(borrowerTestData);
        File uploadFile = new File(TEMP_DIRECTORY + File.separator + UPLOAD_EXCEL_FILE_NAME);
        excelWriter.saveFile(uploadFile);

        // Upload Borrower Data
        FileUploadRequestDataVO fileUploadRequestDataVO = new FileUploadRequestDataVO();
        fileUploadRequestDataVO.setProgramId(AUTOMATION_PROGRAM_ID);
        fileUploadRequestDataVO.setTimestamp(new Date());
        UploadDataResponseVO uploadDataResponseVO = LosCoreService.uploadBorrowers(accessToken, fileUploadRequestDataVO, uploadFile)
                .as(UploadDataResponseVO.class);

        FrameworkAssertions.assertThat(uploadDataResponseVO)
                .hasSameResponseCode("SUCCESS")
                .hasSameResponseMessage("File parsed successfully")
                .timeStampIsNotNull()
                .validationSummaryPathIsNotNull();

        this.validationSummaryFilePath = uploadDataResponseVO.getValidationSummaryPath();

        /*
        FrameworkAssertions.assertThat(uploadDataResponseVO.getSummary())
                .hasSameTotal(1)
                .hasSameUploadedCount(1)
                .hasSameFailedCount(0);
        */
    }

    @Test(description = "Test #188 - Verify that user is able to download output file once file is parsed successfully"
            , dependsOnMethods = "verifyUploadingBulkDataForBorrower"
            , priority = 2)
    public void verifyDownloadingOutputFile() {
        Response response = DocumentVaultService.downloadDocument(this.validationSummaryFilePath);

        String fileName = TestUtils.extractFileNameFromResponseHeader(response);

        String expectedFileName = StringUtils.substringAfterLast(this.validationSummaryFilePath, "/");
        Assertions.assertThat(fileName)
                .as(() -> "Downloaded summary excel file name is not expected")
                .isEqualTo(expectedFileName);

        File summaryFile = TestUtils.convertResponseToTempFile(response, fileName);

        ExcelReader summaryFileExcelWorkbook = new ExcelReader(summaryFile, 0);
        List<String> headerRowCellData = summaryFileExcelWorkbook.getHeaderRowCellData();
        Assertions.assertThat(headerRowCellData)
                .as("Summary file does not contain the desired columns")
                .contains("Status", "Remarks");
    }

    @DataProvider(name = "fileFormatProvider", propagateFailureAsTestFailure = true)
    private Object[][] fileFormatProvider() {
        return new Object[][]{
                {DataIngestionFileFormatEnum.XLS},
                {DataIngestionFileFormatEnum.XML},
                {DataIngestionFileFormatEnum.HTML},
                {DataIngestionFileFormatEnum.JSON}
        };
    }
}
