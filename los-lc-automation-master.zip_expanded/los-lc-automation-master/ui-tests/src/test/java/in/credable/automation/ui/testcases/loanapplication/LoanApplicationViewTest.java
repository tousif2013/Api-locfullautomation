package in.credable.automation.ui.testcases.loanapplication;

import in.credable.automation.ui.steplib.adminapp.loanapplication.LoanApplicationConfigurationPageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Prashant Rana
 */
public class LoanApplicationViewTest extends BaseTest {

    private LoanApplicationConfigurationPageSteps loanApplicationConfigurationPageSteps;

    @BeforeMethod
    public void openLoanApplicationViewPage() {
        loanApplicationConfigurationPageSteps = LoanApplicationConfigurationPageSteps.openLoanApplicationViewPage();
    }

    @Test(description = "Test #546 - Verify the Loan Application View screen components.")
    public void verifyLoanApplicationViewScreenComponents() {
        loanApplicationConfigurationPageSteps.verifyLoanApplicationViewPageComponents();
    }

    @Test(description = "Test #547 - Validate the Loan Application View screen components functionality."
            , priority = 1)
    public void validateLoanApplicationViewScreenComponentsFunctionality() {
        loanApplicationConfigurationPageSteps
                .selectAnySection()
                .verifySectionIsSelected()
                .removeSelectedSection()
                .verifySectionIsRemoved();
    }

    @Test(description = "Test #548 - Test to validate the Create Sections button functionality."
            , priority = 2)
    public void validateCreateSectionsButtonFunctionality() {
        loanApplicationConfigurationPageSteps.createCustomSection()
                .verifyCustomSectionIsCreated()
                .verifyExistingSectionNameCannotBeCreated()
                .updateCustomSectionName()
                .verifyCustomSectionNameIsUpdated()
                .removeCustomSection()
                .verifyCustomSectionIsRemoved();
    }

    @Test(description = "Test #550 - Test to validate the Reset button and Go back functionality."
            , priority = 3)
    public void validateResetButtonAndGoBackFunctionality() {
        loanApplicationConfigurationPageSteps.resetAllSelectedSections()
                .verifyAllSelectedSectionsAreRemoved()
                .verifySaveButtonIsDisabled();
    }
}
