package in.credable.automation.assertions.dataingestion;

import in.credable.automation.service.vo.dataingestion.DataIngestionUploadDataResponseVO;

public final class DataIngestionAssertionsFactory {
    private DataIngestionAssertionsFactory() {
    }

    public static DataIngestionUploadDataResponseAssertion createUploadDataResponseAssertion(DataIngestionUploadDataResponseVO actual) {
        return new DataIngestionUploadDataResponseAssertion(actual);
    }
}
