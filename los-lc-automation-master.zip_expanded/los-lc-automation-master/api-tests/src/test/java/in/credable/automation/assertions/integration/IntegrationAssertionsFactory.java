package in.credable.automation.assertions.integration;

import in.credable.automation.service.vo.integration.VendorVO;

public final class IntegrationAssertionsFactory {
    private IntegrationAssertionsFactory() {
    }

    public static VendorAssertion createVendorAssertion(VendorVO actual) {
        return new VendorAssertion(actual);
    }
}
