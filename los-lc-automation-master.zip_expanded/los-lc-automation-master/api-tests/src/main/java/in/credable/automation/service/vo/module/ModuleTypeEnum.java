package in.credable.automation.service.vo.module;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum ModuleTypeEnum {
    USER_TASK,
    SERVICE_TASK,
    BLOCKING_TASK;

    @JsonCreator
    public static ModuleTypeEnum decode(final String value) {
        return Stream.of(ModuleTypeEnum.values())
                .filter(targetEnum -> targetEnum.name().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
