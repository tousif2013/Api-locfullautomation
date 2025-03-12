package in.credable.automation.assertions.dataingestion;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.dataingestion.DataIngestionUploadDataResponseVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DataIngestionUploadDataResponseAssertion extends CustomAssert<DataIngestionUploadDataResponseAssertion, DataIngestionUploadDataResponseVO> {
    DataIngestionUploadDataResponseAssertion(DataIngestionUploadDataResponseVO actual) {
        super(actual, DataIngestionUploadDataResponseAssertion.class);
    }

    public DataIngestionUploadDataResponseAssertion hasSameResponseCode(String expectedResponseCode) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getResponseCode(), expectedResponseCode, "Response code");
        return this;
    }

    public DataIngestionUploadDataResponseAssertion hasSameResponseMessage(String expectedMessage) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getMessage(), expectedMessage, "Message");
        return this;
    }

    public DataIngestionUploadDataResponseAssertion timeStampIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getTimestamp())) {
            failWithMessage("Timestamp should not be null.");
        }
        return this;
    }

    public DataIngestionUploadDataResponseAssertion rowDataIsNotEmpty() {
        isNotNull();
        if (CollectionUtils.isEmpty(actual.getRowData())) {
            failWithMessage("Raw data should not be empty.");
        }
        return this;
    }

    public DataIngestionUploadDataResponseAssertion allRowsAreValid() {
        isNotNull();
        if (actual.getRowData().stream().anyMatch(map -> !((boolean) map.getOrDefault("isRowValid", false)))) {
            failWithMessage("All rows should be valid.");
        }
        return this;
    }

    public DataIngestionUploadDataResponseAssertion rowIsInvalid(int rowNumber) {
        isNotNull();
        rowNumberIsValid(rowNumber);
        Map<String, Object> map = getRowData(rowNumber);
        if (Objects.isNull(map) || !map.containsKey("isRowValid") || map.get("isRowValid").equals(true)) {
            failWithMessage("Row " + rowNumber + " should be invalid.");
        }
        return this;
    }

    public DataIngestionUploadDataResponseAssertion validationErrorMessagesAreNotEmptyForRow(int rowNumber) {
        isNotNull();
        rowNumberIsValid(rowNumber);
        Map<String, Object> map = getRowData(rowNumber);
        if (Objects.isNull(map) || !map.containsKey("validationErrorMessages")
                || CollectionUtils.isEmpty(getValidationErrorMessagesForRow(rowNumber))) {
            failWithMessage("Validation error messages should not be empty for row " + rowNumber);
        }
        return this;
    }

    public DataIngestionUploadDataResponseAssertion validationErrorMessageIsPresentForField(int rowNumber, String fieldName) {
        isNotNull();
        List<Map<String, String>> validationErrorMessages = getValidationErrorMessagesForRow(rowNumber);
        if (validationErrorMessages.stream()
                .noneMatch(map -> map.get("fieldName").equals(fieldName))) {
            failWithMessage("Validation error message for field %s should be present for row %d".formatted(fieldName, rowNumber));
        }
        return this;
    }

    public DataIngestionUploadDataResponseAssertion hasSameValidationErrorMessageForField(int rowNumber, String fieldName, String expectedErrorMessage) {
        isNotNull();
        List<Map<String, String>> validationErrorMessages = getValidationErrorMessagesForRow(rowNumber);
        validationErrorMessages.stream()
                .filter(map -> map.get("fieldName").equals(fieldName))
                .findFirst()
                .ifPresentOrElse(map -> failureWithActualExpectedForStringComparison(map.get("errorMessage"),
                                expectedErrorMessage,
                                "Validation error message for field %s".formatted(fieldName)),
                        () -> failWithMessage("Validation error message for field %s should be present for row %d".formatted(fieldName, rowNumber)));
        return this;
    }

    public DataIngestionUploadDataResponseAssertion validationSummaryPathIsNotNull() {
        isNotNull();
        if (StringUtils.isBlank(actual.getValidationSummaryPath())) {
            failWithMessage("Validation summary path should not be null.");
        }
        return this;
    }

    private Map<String, Object> getRowData(int rowNumber) {
        return actual.getRowData().get(rowNumber - 1);
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, String>> getValidationErrorMessagesForRow(int rowNumber) {
        Map<String, Object> rowData = getRowData(rowNumber);
        return (List<Map<String, String>>) rowData.get("validationErrorMessages");
    }

    private void rowNumberIsValid(int rowNumber) {
        if (rowNumber > actual.getRowData().size()) {
            failWithMessage("Row number should be less than or equal to " + actual.getRowData().size());
        }
    }
}
