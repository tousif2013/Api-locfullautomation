package in.credable.automation.enums;

import lombok.Getter;

@Getter
public enum MimeType {
    JSON("application/json"),

    EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    TEXT("text/plain"),
    IMAGE("image/png");

    private final String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
