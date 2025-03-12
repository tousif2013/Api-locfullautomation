package in.credable.automation.ui.testcases.dealer;

import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.dealer.DealerPageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DealerPageCurrencyTest extends BaseTest {
    private DealerPageSteps dealerPageSteps;

    @BeforeClass
    public void navigateToDealersPage() {
        dealerPageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .selectProgramForDealerAutoApproved()
                .navigateToDealerPage()
                .verifyDealerPageIsOpened();
    }

    @Test(description = "Test #705 - Test to validate that the currency showing on the Dealers > Processed screen.")
    public void verifyCurrencyShowingOnDealerPage() {
        dealerPageSteps.openDealerDetails()
                .verifyCurrencyShowingOnDealerDetailsModal();
    }
}
