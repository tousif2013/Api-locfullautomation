package in.credable.automation.assertions.core;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.LoanApplicationStatusEnum;
import in.credable.automation.service.vo.core.LoanApplicationVO;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class LoanApplicationAssertion extends CustomAssert<LoanApplicationAssertion, LoanApplicationVO> {
    LoanApplicationAssertion(LoanApplicationVO actual) {
        super(actual, LoanApplicationAssertion.class);
    }

    public LoanApplicationAssertion loanApplicationIdIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getId())) {
            failWithMessage("loan application id is null");
        }
        return this;
    }

    public LoanApplicationAssertion hasSameId(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getId(), expected, "id");
        return this;
    }

    public LoanApplicationAssertion loanNumberIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getLoanNumber())) {
            failWithMessage("loan number is null");
        }
        return this;
    }

    public LoanApplicationAssertion loanNumberStartsWith(String expectedPrefix) {
        isNotNull();
        if (!StringUtils.startsWith(actual.getLoanNumber(), expectedPrefix)) {
            failWithMessage("loan number does not start with %s", expectedPrefix);
        }
        return this;
    }

    public LoanApplicationAssertion hasSameLoanNumber(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getLoanNumber(), expected, "loanNumber");
        return this;
    }

    public LoanApplicationAssertion statusIsCreated() {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getStatus(),
                LoanApplicationStatusEnum.CREATED.name(),
                "Loan application status");
        return this;
    }

    public LoanApplicationAssertion hasSameStatus(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getStatus(), expected, "status");
        return this;
    }

    public LoanApplicationAssertion hasSameProgramId(Long expected) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getProgramId(), expected, "programId");
        return this;
    }

    public LoanApplicationAssertion hasSameBorrowerId(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getBorrowerId(), expected, "borrowerId");
        return this;
    }

    public LoanApplicationAssertion hasSameModuleCode(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleCode(), expected, "moduleCode");
        return this;
    }

    public LoanApplicationAssertion hasSameModuleInstanceName(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleInstanceName(), expected, "moduleInstanceName");
        return this;
    }

    public LoanApplicationAssertion hasSameErrorCode(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getErrorCode(), expected, "errorCode");
        return this;
    }

    public LoanApplicationAssertion hasSameErrorDetails(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getErrorDetails(), expected, "errorDetails");
        return this;
    }

    public LoanApplicationAssertion hasSameFlowableProcessInstanceId(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getFlowableProcessInstanceId(), expected, "flowableProcessInstanceId");
        return this;
    }

}
