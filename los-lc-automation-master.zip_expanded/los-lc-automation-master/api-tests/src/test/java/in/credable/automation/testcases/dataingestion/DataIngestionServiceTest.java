package in.credable.automation.testcases.dataingestion;

import in.credable.automation.assertions.FrameworkAssertions;
import in.credable.automation.commons.excel.ExcelWriter;
import in.credable.automation.commons.utils.FileUtil;
import in.credable.automation.dataprovider.TestDataProvider;
import in.credable.automation.enums.StandardField;
import in.credable.automation.service.dataingestion.DataIngestionService;
import in.credable.automation.service.dataingestion.DealerStandardFieldMappings;
import in.credable.automation.service.vo.dataingestion.DataIngestionFileUploadRequestDataVO;
import in.credable.automation.service.vo.dataingestion.DataIngestionUploadDataResponseVO;
import in.credable.automation.testcases.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Date;
import java.util.Map;

public class DataIngestionServiceTest extends BaseTest {

    private DataIngestionFileUploadRequestDataVO dataIngestionFileUploadRequestDataVO;

    @BeforeMethod
    public void prepareFileUploadRequestData() {
        dataIngestionFileUploadRequestDataVO = new DataIngestionFileUploadRequestDataVO();
        dataIngestionFileUploadRequestDataVO.setCustomPropertyMappings(TestDataProvider.getBorrowerCustomPropertyMappings());
        dataIngestionFileUploadRequestDataVO.setTimestamp(new Date());
    }

    @Test(description = "Test #41 - Verify if user uploads excel with valid data as per custom properties mappings then " +
            "the file is uploaded successfully and user gets success response")
    public void verifyUploadingExcelWithValidInputData() {
        Map<String, Object> borrowerTestData = TestDataProvider.getBorrowerTestData();

        File tempFile = createAndWriteDataIntoExcel(borrowerTestData);

        DataIngestionUploadDataResponseVO dataIngestionUploadDataResponseVO = DataIngestionService.uploadBorrowers(dataIngestionFileUploadRequestDataVO, tempFile)
                .as(DataIngestionUploadDataResponseVO.class);

        FrameworkAssertions.assertThat(dataIngestionUploadDataResponseVO)
                .hasSameResponseCode("CRED1")
                .hasSameResponseMessage("File parsed successfully")
                .timeStampIsNotNull()
                .rowDataIsNotEmpty()
                .allRowsAreValid()
                .validationSummaryPathIsNotNull();
    }

    @Test(description = "Test #58 - Verify if property validator is set to RANGE in json data and property value is " +
            "outside of the range in the excel file then user gets error response")
    public void verifyUploadingExcelWithInvalidRange() {
        Map<String, Object> borrowerTestData = TestDataProvider.getBorrowerTestData();
        Map<StandardField, String> dealerFieldsMappings = DealerStandardFieldMappings.getDealerFieldsMappings();
        String creditPeriodCustomName = dealerFieldsMappings.get(StandardField.CREDIT_PERIOD);
        borrowerTestData.put(creditPeriodCustomName, 181);

        File tempFile = createAndWriteDataIntoExcel(borrowerTestData);

        DataIngestionUploadDataResponseVO dataIngestionUploadDataResponseVO = DataIngestionService.uploadBorrowers(dataIngestionFileUploadRequestDataVO, tempFile)
                .as(DataIngestionUploadDataResponseVO.class);

        FrameworkAssertions.assertThat(dataIngestionUploadDataResponseVO)
                .hasSameResponseCode("CRED1")
                .hasSameResponseMessage("File parsed successfully")
                .timeStampIsNotNull()
                .rowDataIsNotEmpty()
                .rowIsInvalid(1)
                .validationErrorMessagesAreNotEmptyForRow(1)
                .validationErrorMessageIsPresentForField(1, creditPeriodCustomName)
                .hasSameValidationErrorMessageForField(1, creditPeriodCustomName, creditPeriodCustomName + " must be between (0, 180)")
                .validationSummaryPathIsNotNull();
    }

    @Test(description = "Test #59 -Verify when MANDATORY and REGEX validations are given for a field and REGEX validation is failing " +
            "then the user gets error response")
    public void verifyUploadingExcelWithInvalidRegex() {
        Map<String, Object> borrowerTestData = TestDataProvider.getBorrowerTestData();
        Map<StandardField, String> dealerFieldsMappings = DealerStandardFieldMappings.getDealerFieldsMappings();
        String dealerCodeCustomName = dealerFieldsMappings.get(StandardField.BORROWER_CODE);
        borrowerTestData.put(dealerCodeCustomName, "C()DE");

        File tempFile = createAndWriteDataIntoExcel(borrowerTestData);

        DataIngestionUploadDataResponseVO dataIngestionUploadDataResponseVO = DataIngestionService.uploadBorrowers(dataIngestionFileUploadRequestDataVO, tempFile)
                .as(DataIngestionUploadDataResponseVO.class);

        FrameworkAssertions.assertThat(dataIngestionUploadDataResponseVO)
                .hasSameResponseCode("CRED1")
                .hasSameResponseMessage("File parsed successfully")
                .timeStampIsNotNull()
                .rowDataIsNotEmpty()
                .rowIsInvalid(1)
                .validationErrorMessagesAreNotEmptyForRow(1)
                .validationErrorMessageIsPresentForField(1, dealerCodeCustomName)
                .hasSameValidationErrorMessageForField(1, dealerCodeCustomName, dealerCodeCustomName + " is not valid")
                .validationSummaryPathIsNotNull();
    }

    @Test(description = "Test #60 -Verify when MANDATORY and PAN validations are given for a field and PAN validation is failing " +
            "then the user gets error response")
    public void verifyUploadingExcelWithInvalidPan() {
        Map<String, Object> borrowerTestData = TestDataProvider.getBorrowerTestData();
        Map<StandardField, String> dealerFieldsMappings = DealerStandardFieldMappings.getDealerFieldsMappings();
        String panCustomName = dealerFieldsMappings.get(StandardField.PAN);
        borrowerTestData.put(panCustomName, "ABCDE");

        File tempFile = createAndWriteDataIntoExcel(borrowerTestData);

        DataIngestionUploadDataResponseVO dataIngestionUploadDataResponseVO = DataIngestionService.uploadBorrowers(dataIngestionFileUploadRequestDataVO, tempFile)
                .as(DataIngestionUploadDataResponseVO.class);

        FrameworkAssertions.assertThat(dataIngestionUploadDataResponseVO)
                .hasSameResponseCode("CRED1")
                .hasSameResponseMessage("File parsed successfully")
                .timeStampIsNotNull()
                .rowDataIsNotEmpty()
                .rowIsInvalid(1)
                .validationErrorMessagesAreNotEmptyForRow(1)
                .validationErrorMessageIsPresentForField(1, panCustomName)
                .hasSameValidationErrorMessageForField(1, panCustomName, "ABCDE is not a valid PAN")
                .validationSummaryPathIsNotNull();
    }

    @Test(description = "Test #108 - Verify if user sends json data with UNIQUE constraint for a field then user gets " +
            "error response for duplicate value for the specific column in the excel")
    public void verifyUploadingExcelWithDuplicateValue() {
        Map<String, Object> borrowerTestData = TestDataProvider.getBorrowerTestData();

        ExcelWriter excelWriter = new ExcelWriter("Dealers");
        excelWriter.createHeaderRow(borrowerTestData.keySet().toArray(new String[0]));

        excelWriter.writeDataIntoNewRow(borrowerTestData);
        excelWriter.writeDataIntoNewRow(borrowerTestData);

        File tempFile = FileUtil.createTempFile("upload.xlsx");
        excelWriter.saveFile(tempFile);

        DataIngestionUploadDataResponseVO dataIngestionUploadDataResponseVO = DataIngestionService.uploadBorrowers(dataIngestionFileUploadRequestDataVO, tempFile)
                .as(DataIngestionUploadDataResponseVO.class);

        Map<StandardField, String> dealerFieldsMappings = DealerStandardFieldMappings.getDealerFieldsMappings();
        String panCustomName = dealerFieldsMappings.get(StandardField.PAN);

        FrameworkAssertions.assertThat(dataIngestionUploadDataResponseVO)
                .hasSameResponseCode("CRED1")
                .hasSameResponseMessage("File parsed successfully")
                .timeStampIsNotNull()
                .rowDataIsNotEmpty()
                .rowIsInvalid(2)
                .validationSummaryPathIsNotNull()
                .validationErrorMessagesAreNotEmptyForRow(2)
                .validationErrorMessageIsPresentForField(2, panCustomName)
                .hasSameValidationErrorMessageForField(2, panCustomName, panCustomName + " is used already");
    }

    private File createAndWriteDataIntoExcel(Map<String, Object> borrowerTestData) {
        ExcelWriter excelWriter = new ExcelWriter("Dealers");
        excelWriter.createHeaderRow(borrowerTestData.keySet().toArray(new String[0]));

        excelWriter.writeDataIntoNewRow(borrowerTestData);

        File tempFile = FileUtil.createTempFile("upload.xlsx");
        excelWriter.saveFile(tempFile);
        return tempFile;
    }
}
