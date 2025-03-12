package in.credable.automation.service.vo.integration;

import lombok.Getter;


@Getter
public enum VendorType {
    BSA,
    MCA,
    PAN_GST("PAN/GST"),
    CRIME_CHECK("crimeCheck"),
    WATCH_OUT("watchOut");
    private final String value;

    VendorType(String value) {
        this.value = value;
    }

    VendorType() {
        this.value = name();
    }
}