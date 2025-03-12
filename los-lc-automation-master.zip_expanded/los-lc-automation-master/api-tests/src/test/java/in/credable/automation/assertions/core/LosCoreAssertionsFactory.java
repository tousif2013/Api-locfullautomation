package in.credable.automation.assertions.core;

import in.credable.automation.service.vo.core.*;

public final class LosCoreAssertionsFactory {
    private LosCoreAssertionsFactory() {
    }

    public static UploadDataResponseAssertion createUploadDataResponseAssertion(UploadDataResponseVO actual) {
        return new UploadDataResponseAssertion(actual);
    }

    public static UploadSummaryAssertion createUploadSummaryAssertion(UploadSummaryVO actual) {
        return new UploadSummaryAssertion(actual);
    }

    public static BorrowerResponseAssertion createBorrowerResponseAssertion(BorrowerResponseVO actual) {
        return new BorrowerResponseAssertion(actual);
    }

    public static LoanApplicationAssertion createLoanApplicationAssertion(LoanApplicationVO actual) {
        return new LoanApplicationAssertion(actual);
    }

    public static ModuleExecutionInfoResponseAssertion createModuleExecutionInfoResponseAssertion(ModuleExecutionInfoResponseVO actual) {
        return new ModuleExecutionInfoResponseAssertion(actual);
    }
}
