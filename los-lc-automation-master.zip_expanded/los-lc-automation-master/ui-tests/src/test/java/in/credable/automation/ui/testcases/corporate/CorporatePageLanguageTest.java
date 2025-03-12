package in.credable.automation.ui.testcases.corporate;

import in.credable.automation.ui.steplib.adminapp.AdminAppCommonSteps;
import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.CorporatePageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CorporatePageLanguageTest extends BaseTest {

    private CorporatePageSteps corporatePageSteps;

    @BeforeClass
    public void navigateToCorporatePage() {
        corporatePageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .navigateToCorporatePage()
                .verifyCorporatePageIsOpened();
    }

    @BeforeClass(dependsOnMethods = "navigateToCorporatePage")
    public void changeLanguageToHindi() {
        AdminAppCommonSteps.changeLanguageToHindi();
    }

    @Test(description = "Test #553 - Test to validate the language conversion functionality for the Corporate module.")
    public void verifyLanguageConversionFunctionality() {
        corporatePageSteps.verifyLanguageConversionOnCorporateDashboardPage()
                .openCreateCorporateModal()
                .verifyCreateOrEditCorporateModalLabels()
                .verifyMandatoryFieldsInCreateCorporateModal()
                .createNewCorporateWithMandatoryDetails()
                .verifyCorporateDetailsAreDisplayedInTheFirstRow()
                .openCorporateDetailsModalForCreatedCorporate()
                .verifyCorporateDetailsModalLabels()
                .closeCorporateDetailsModal()
                .openEditCorporateDetailsModalForCreatedCorporate()
                .verifyCreateOrEditCorporateModalLabels()
                .closeEditCorporateDetailsModal();
    }

    @AfterClass
    public void changeLanguageToEnglish() {
        AdminAppCommonSteps.changeLanguageToEnglish();
    }
}
