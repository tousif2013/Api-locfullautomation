package in.credable.automation.service.vo.core;

import lombok.Getter;

@Getter
public enum DataIngestionFileFormatEnum {
    XLSX("xlsx"),
    XLS("xls"),
    XML("xml"),
    HTML("html"),
    JSON("json");

    private final String extension;

    DataIngestionFileFormatEnum(String extension) {
        this.extension = extension;
    }
}
