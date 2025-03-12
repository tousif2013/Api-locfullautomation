package in.credable.automation.service.vo.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ModuleExecutionStatusEnum {
    PENDING("Pending"),
    IN_PROGRESS("InProgress"),
    COMPLETED("Completed"),
    ERROR("Error"),
    FAILED("Failed"),
    SYNCPART_COMPLETED("SyncPartCompleted");

    private final String value;
}
