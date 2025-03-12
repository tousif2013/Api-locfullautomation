package in.credable.automation.assertions;

import in.credable.automation.service.vo.module.ModuleCode;
import in.credable.automation.service.vo.module.ModuleInstanceVO;
import in.credable.automation.service.vo.module.ModuleTypeEnum;
import in.credable.automation.service.vo.module.ModuleVO;
import org.assertj.core.api.Assertions;

import java.util.Objects;

public final class ModuleInstanceAssertion extends CustomAssert<ModuleInstanceAssertion, ModuleInstanceVO> {
    ModuleInstanceAssertion(ModuleInstanceVO actual) {
        super(actual, ModuleInstanceAssertion.class);
    }

    public ModuleInstanceAssertion moduleInstanceIdIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getId())) {
            failWithMessage("Module instance id should not be null.");
        }
        return this;
    }

    public ModuleInstanceAssertion hasSameId(Long expectedId) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getId(), expectedId, "Module instance id");
        return this;
    }

    public ModuleInstanceAssertion hasSameModuleId(Long expectedModuleId) {
        isNotNull();
        failureWithActualExpectedForNumberComparison(actual.getModuleId(), expectedModuleId, "Module id");
        return this;
    }

    public ModuleInstanceAssertion moduleInstanceNameIs(String expectedName) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleInstanceName(), expectedName, "Module instance name");
        return this;
    }

    public ModuleInstanceAssertion isModuleInstanceOfModule(ModuleVO moduleVO) {
        isNotNull();
        ModuleTypeEnum moduleType = ModuleTypeEnum.decode(moduleVO.getModuleType());
        if (ModuleTypeEnum.USER_TASK.equals(moduleType)) {
            Assertions.assertThat(actual)
                    .as(() -> "module instance data key and data json should be null for user module")
                    .extracting(ModuleInstanceVO::getModuleInstanceDataKey, ModuleInstanceVO::getModuleInstanceDataJson)
                    .containsOnlyNulls();

            Assertions.assertThat(actual.getModuleInstanceUiKey())
                    .as(() -> "module instance UI key should not be null or empty for user module")
                    .isNotNull()
                    .isNotEmpty();

            Assertions.assertThat(actual.getModuleInstanceUiJson())
                    .as(() -> "module instance UI json should not be null or empty for user module")
                    .isNotNull()
                    .isNotEmpty()
                    .as(() -> "module instance UI json should be same as module json")
                    .isEqualTo(Objects.requireNonNullElse(moduleVO.getModuleJson(), "{}"));
        } else if (ModuleTypeEnum.SERVICE_TASK.equals(moduleType)) {
            Assertions.assertThat(actual)
                    .as(() -> "module instance UI key and UI json should be null for service module")
                    .extracting(ModuleInstanceVO::getModuleInstanceUiKey, ModuleInstanceVO::getModuleInstanceUiJson)
                    .containsOnlyNulls();

            Assertions.assertThat(actual.getModuleInstanceDataKey())
                    .as(() -> "module instance data key should not be null or empty for service module")
                    .isNotNull()
                    .isNotEmpty();

            Assertions.assertThat(actual.getModuleInstanceDataJson())
                    .as(() -> "module instance data json should not be null or empty for service module")
                    .isNotNull()
                    .isNotEmpty()
                    .as(() -> "module instance data json should be same as module json")
                    .isEqualTo(Objects.requireNonNullElse(moduleVO.getModuleJson(), "[]"));
        }
        return this;
    }

    public ModuleInstanceAssertion hasSameModuleInstanceDataKey(String moduleInstanceDataKey) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleInstanceDataKey(), moduleInstanceDataKey, "moduleInstanceDataKey");
        return this;
    }

    public ModuleInstanceAssertion hasSameModuleInstanceDataJson(String moduleInstanceDataJson) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getModuleInstanceDataJson(), moduleInstanceDataJson, "moduleInstanceDataJson");
        return this;
    }

    public ModuleInstanceAssertion hasSameModuleCode(ModuleCode moduleCode) {
        isNotNull();
        super.failureWithActualExpectedForEnumComparison(actual.getModuleCode(), moduleCode, "moduleCode");
        return this;
    }

    public ModuleInstanceAssertion hasSameTaskType(String taskType) {
        isNotNull();
        failureWithActualExpectedForStringComparison(actual.getTaskType(), taskType, "taskType");
        return this;
    }
}
