package in.credable.automation.service.vo.program;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ProgramStatus {
    DRAFT("Draft"),
    APPROVAL_PENDING("Approval Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    PUBLISHED("Published"),
    INACTIVE("Inactive");

    private final String value;

    ProgramStatus(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ProgramStatus decode(final String value) {
        return Stream.of(ProgramStatus.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }
}
