package in.credable.automation.ui.testcases.dealer;

import in.credable.automation.ui.steplib.adminapp.AdminAppCommonSteps;
import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.dealer.DealerPageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DealerPageLanguageTest extends BaseTest {

    private DealerPageSteps dealerPageSteps;

    @BeforeClass
    public void navigateToDealersPage() {
        dealerPageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .navigateToDealerPage()
                .verifyDealerPageIsOpened();
    }

    @BeforeClass(dependsOnMethods = "navigateToDealersPage")
    public void changeLanguageToHindi() {
        AdminAppCommonSteps.changeLanguageToHindi();
    }

    @Test(description = "Test #698 - Test to validate the language conversion functionality for the Processed screen's under dealer module.")
    public void verifyLanguageChangeFunctionalityForProcessedTab() {
        dealerPageSteps.verifyLanguageConversionOnDealersPageUnderProcessedTab();
    }

    @Test(description = "Test #554 - Test to validate the language conversion functionality for the Approval Pending screen's under Dealers module.")
    public void verifyLanguageChangeFunctionalityForApprovalPendingTab() {
        dealerPageSteps.verifyLanguageConversionOnDealersPageUnderApprovalPendingTab();
    }

    @AfterClass
    public void changeLanguageToEnglish() {
        AdminAppCommonSteps.changeLanguageToEnglish();
    }
}
