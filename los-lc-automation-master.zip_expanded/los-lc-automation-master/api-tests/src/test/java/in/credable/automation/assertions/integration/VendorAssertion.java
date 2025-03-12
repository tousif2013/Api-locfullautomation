package in.credable.automation.assertions.integration;

import in.credable.automation.assertions.CustomAssert;
import in.credable.automation.service.vo.integration.VendorVO;

import java.util.Objects;

public final class VendorAssertion extends CustomAssert<VendorAssertion, VendorVO> {
    VendorAssertion(VendorVO actual) {
        super(actual, VendorAssertion.class);
    }

    public VendorAssertion vendorIdIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getId())) {
            failWithMessage("Vendor id should not be null.");
        }
        return this;
    }

    public VendorAssertion vendorNameIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getVendorName(), expected, "Vendor name");
        return this;
    }

    public VendorAssertion vendorTypeIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getVendorType(), expected, "Vendor type");
        return this;
    }

    public VendorAssertion descriptionIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getDescription(), expected, "Description");
        return this;
    }

    public VendorAssertion countryCodeIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getCountryCode(), expected, "Country code");
        return this;
    }

    public VendorAssertion statusIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getStatus().toString(), expected, "Status");
        return this;
    }

    public VendorAssertion createdAtIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getCreatedAt())) {
            failWithMessage("CreatedAt should not be null.");
        }
        return this;
    }

    public VendorAssertion createdByIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getCreatedBy(), expected, "Created by");
        return this;
    }

    public VendorAssertion updatedAtIsNotNull() {
        isNotNull();
        if (Objects.isNull(actual.getUpdatedAt())) {
            failWithMessage("UpdatedAt should not be null.");
        }
        return this;
    }

    public VendorAssertion updatedByIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getUpdatedBy(), expected, "Updated by");
        return this;
    }

    public VendorAssertion vendorIdIs(String expected) {
        isNotNull();
        super.failureWithActualExpectedForStringComparison(actual.getId(), expected, "Vendor id");
        return this;
    }
}
