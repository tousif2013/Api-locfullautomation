package in.credable.automation.ui.testcases.vendor;

import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.vendor.VendorPageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VendorPageCurrencyTest extends BaseTest {
    private VendorPageSteps vendorPageSteps;

    @BeforeClass
    public void navigateToVendorsPage() {
        vendorPageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .selectProgramForVendorAutoApproved()
                .navigateToVendorPage()
                .verifyVendorPageIsOpened();
    }

    @Test(description = "Test #706 - Test to validate that the currency showing on the Vendors < Processed screen.")
    public void verifyCurrencyShowingOnVendorPage() {
        vendorPageSteps.openVendorDetails()
                .verifyCurrencyShowingOnVendorDetailsModal();
    }
}
