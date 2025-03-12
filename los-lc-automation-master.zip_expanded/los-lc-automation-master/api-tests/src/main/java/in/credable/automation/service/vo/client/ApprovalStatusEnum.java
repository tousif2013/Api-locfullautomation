package in.credable.automation.service.vo.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum ApprovalStatusEnum {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String value;

    ApprovalStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static ApprovalStatusEnum decode(final String value) {
        return Stream.of(ApprovalStatusEnum.values())
                .filter(targetEnum -> targetEnum.value.equals(value))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
