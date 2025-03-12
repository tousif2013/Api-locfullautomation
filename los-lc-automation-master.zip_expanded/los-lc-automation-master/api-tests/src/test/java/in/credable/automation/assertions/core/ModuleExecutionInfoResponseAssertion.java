package in.credable.automation.assertions.core;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.core.ModuleExecutionInfoResponseVO;
import in.credable.automation.service.vo.core.ModuleExecutionStatusEnum;

public final class ModuleExecutionInfoResponseAssertion extends CustomAssert<ModuleExecutionInfoResponseAssertion, ModuleExecutionInfoResponseVO> {
    ModuleExecutionInfoResponseAssertion(ModuleExecutionInfoResponseVO moduleExecutionInfoResponseVO) {
        super(moduleExecutionInfoResponseVO, ModuleExecutionInfoResponseAssertion.class);
    }

    public ModuleExecutionInfoResponseAssertion loanApplicationIdIs(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getLoanApplicationId(), expected, "loanApplicationId");
        return this;
    }

    public ModuleExecutionInfoResponseAssertion moduleCodeIs(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleCode(), expected, "moduleCode");
        return this;
    }

    public ModuleExecutionInfoResponseAssertion moduleInstanceIdIs(String expected) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleInstanceId(), expected, "moduleInstanceId");
        return this;
    }

    public ModuleExecutionInfoResponseAssertion moduleExecutionStatusIs(ModuleExecutionStatusEnum expected) {
        isNotNull();
        failureWithActualExpectedForEnumComparison(actual.getModuleExecutionStatus(), expected, "moduleExecutionStatus");
        return this;
    }

    public ModuleExecutionInfoResponseAssertion executionResultIsNotEmpty() {
        isNotNull();
        super.propertyIsNotNull(actual.getExecutionResult(), "executionResult");
        if (actual.getExecutionResult().isEmpty()) {
            failWithMessage("Execution result is empty");
        }
        return this;
    }

}
