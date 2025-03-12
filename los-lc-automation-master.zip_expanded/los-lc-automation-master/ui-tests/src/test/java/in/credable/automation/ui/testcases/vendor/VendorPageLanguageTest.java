package in.credable.automation.ui.testcases.vendor;

import in.credable.automation.ui.steplib.adminapp.AdminAppCommonSteps;
import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.vendor.VendorPageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class VendorPageLanguageTest extends BaseTest {
    private VendorPageSteps vendorPageSteps;

    @BeforeClass
    public void navigateToVendorsPage() {
        vendorPageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .navigateToVendorPage()
                .verifyVendorPageIsOpened();
    }

    @BeforeClass(dependsOnMethods = "navigateToVendorsPage")
    public void changeLanguageToHindi() {
        AdminAppCommonSteps.changeLanguageToHindi();
    }

    @Test(description = "Test #555 - Test to validate the language conversion functionality for the Processed screen's under Vendors module.")
    public void verifyLanguageChangeFunctionalityForProcessedTab() {
        vendorPageSteps.verifyLanguageConversionOnVendorPageUnderProcessedTab();
    }

    @Test(description = "Test #697 - Test to validate the language conversion functionality for the Approval Pending screen under Vendors module.")
    public void verifyLanguageChangeFunctionalityForApprovalPendingTab() {
        vendorPageSteps.verifyLanguageConversionOnVendorPageUnderApprovalPendingTab();
    }

    @AfterClass
    public void changeLanguageToEnglish() {
        AdminAppCommonSteps.changeLanguageToEnglish();
    }
}
