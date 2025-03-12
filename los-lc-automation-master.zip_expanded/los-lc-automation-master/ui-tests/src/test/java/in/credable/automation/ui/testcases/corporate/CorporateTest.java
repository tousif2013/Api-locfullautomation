package in.credable.automation.ui.testcases.corporate;

import in.credable.automation.ui.steplib.adminapp.AdminAppDashboardPageSteps;
import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.CorporatePageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CorporateTest extends BaseTest {
    private AdminAppDashboardPageSteps adminAppDashboardPageSteps;
    private CorporatePageSteps corporatePageSteps;

    @BeforeClass
    public void loginToAdminApp() {
        adminAppDashboardPageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .verifyAdminAppDashboardPageIsOpened();
    }

    @BeforeMethod
    public void navigateToCorporatePage() {
        corporatePageSteps = adminAppDashboardPageSteps.navigateToCorporatePage()
                .verifyCorporatePageIsOpened();
    }

    @Test(description = "Test #213 - Verify Corporate can be added with mandatory fields, " +
            "Test #214 - Verify API triggers for PAN and GSTIN verification, " +
            "Test #217 - Verify mandatory field validation, " +
            "Test #229 - Verify that user is able to view created corporate, " +
            "Test #234 - Verify that user is able to Approve Pending corporate, " +
            "Test #233 - Verify that user is unable to edit any details other then subsegment in approved corporate ," +
            "Test #231 - Verify that user is able to add subsegment in approved corporate")
    public void verifyAddingCorporateWithMandatoryFields() {
        corporatePageSteps
                .openCreateCorporateModal()
                // Test #217 - Verify mandatory field validation
                .verifyMandatoryFieldsInCreateCorporateModal()
                .createNewCorporateWithMandatoryDetails()
                .verifyCorporateDetailsAreDisplayedInTheFirstRow()
                // Test #229 - Verify that user is able to view created corporate
                .openCorporateDetailsModalForCreatedCorporate()
                .verifyCorporateDetailsModalDetails()
                // Test #234 - Verify that user is able to Approve Pending corporate
                .approveCorporate()
                .closeCorporateDetailsModal()
                .verifyCorporateIsApproved()
                .openEditCorporateDetailsModalForCreatedCorporate()
                // Test #233 - Verify that user is unable to edit any details other than subsegment in approved corporate
                .verifyAllFieldsAreDisabledExceptSubsegment()
                // Test #231 - Verify that user is able to add subsegment in approved corporate
                .addSubSegment()
                .verifyCorporateDetailsAreDisplayedInTheFirstRow()
                .openCorporateDetailsModalForCreatedCorporate()
                .verifyCorporateDetailsModalDetails();
    }

    @Test(description = "Test #215 - Verify addition of Corporate Subsegment, " +
            "Test #216 - Verify addition of additional fields, " +
            "Test #230 - Verify that user is able to edit pending corporates, " +
            "Test #235 - Verify that user is able to Reject Pending corporate, " +
            "Test #232 - Verify that user is not able to edit Reject created corporate.")
    public void verifyAddingCorporateWithAllFields() {
        corporatePageSteps
                .openCreateCorporateModal()
                .createNewCorporateWithAllDetails()
                .verifyCorporateDetailsAreDisplayedInTheFirstRow()
                .openCorporateDetailsModalForCreatedCorporate()
                .verifyCorporateDetailsModalDetails()
                .closeCorporateDetailsModal()
                // Test #230 - Verify that user is able to edit pending corporates
                .openEditCorporateDetailsModalForCreatedCorporate()
                .updateCorporateWithMandatoryDetails()
                .verifyCorporateDetailsAreDisplayedInTheFirstRow()
                .openCorporateDetailsModalForCreatedCorporate()
                .verifyCorporateDetailsModalDetails()
                // Test #235 - Verify that user is able to Reject Pending corporate
                .rejectCorporate()
                .closeCorporateDetailsModal()
                .verifyCorporateIsRejected()
                .verifyRejectedCorporateIsNotEditable();
    }
}
