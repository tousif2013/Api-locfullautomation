package in.credable.automation.assertions.core;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.UploadSummaryVO;

public class UploadSummaryAssertion extends CustomAssert<UploadSummaryAssertion, UploadSummaryVO> {
    UploadSummaryAssertion(UploadSummaryVO uploadSummaryVO) {
        super(uploadSummaryVO, UploadSummaryAssertion.class);
    }

    public UploadSummaryAssertion hasSameTotal(Integer total) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getTotal(), total, "Total");
        return this;
    }

    public UploadSummaryAssertion hasSameUploadedCount(Integer uploadedCount) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getUploaded(), uploadedCount, "Uploaded count");
        return this;
    }

    public UploadSummaryAssertion hasSameFailedCount(Integer failedCount) {
        isNotNull();
        super.failureWithActualExpectedForNumberComparison(actual.getFailed(), failedCount, "Failed count");
        return this;
    }

}
