package in.credable.automation.service.vo.core;

import lombok.Getter;

@Getter
public enum BorrowerTypeEnum {

    DEALER(1),
    VENDOR(2),
    INDIVIDUAL(3);

    private final long value;

    BorrowerTypeEnum(long value) {
        this.value = value;
    }

    public static BorrowerTypeEnum resolve(long value) {
        for (BorrowerTypeEnum enumValue: BorrowerTypeEnum.values()) {
            if (enumValue.getValue() == value) {
                return enumValue;
            }
        }
        return null;
    }

}
