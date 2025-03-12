package in.credable.automation.ui.steplib.adminapp.loanapplication;

import in.credable.automation.ui.assertions.FrameworkAssertions;
import in.credable.automation.ui.pages.adminapp.loanapplication.LoanApplicationConfigurationPage;
import in.credable.automation.ui.pages.components.ToastNotification;
import in.credable.automation.ui.utils.SelenideUtils;
import in.credable.automation.utils.RandomDataGenerator;
import org.assertj.core.api.Assertions;

public class LoanApplicationConfigurationPageSteps {
    private final LoanApplicationConfigurationPage loanApplicationConfigurationPage;
    private String selectedSectionName;
    private String customSectionName;
    private int availableSectionsCountBeforeReset;
    private int selectedSectionsCountBeforeReset;
    private int selectedSectionsCountBeforeAddingNewSection;

    private LoanApplicationConfigurationPageSteps() {
        loanApplicationConfigurationPage = new LoanApplicationConfigurationPage();
    }

    public static LoanApplicationConfigurationPageSteps openLoanApplicationViewPage() {
        LoanApplicationConfigurationPageSteps loanApplicationConfigurationPageSteps = new LoanApplicationConfigurationPageSteps();
        loanApplicationConfigurationPageSteps.loanApplicationConfigurationPage.open();
        return loanApplicationConfigurationPageSteps;
    }

    public LoanApplicationConfigurationPageSteps verifyLoanApplicationViewPageComponents() {
        Assertions.assertThat(loanApplicationConfigurationPage.isBackButtonDisplayed())
                .as("Back button is not displayed")
                .isTrue();
        Assertions.assertThat(loanApplicationConfigurationPage.clickBackButton().isModuleConfigurationPageDisplayed())
                .as("Module Configuration Page is not displayed")
                .isTrue();
        SelenideUtils.goBack();
        Assertions.assertThat(loanApplicationConfigurationPage.getPageTitle())
                .as("Loan application view page title is not expected")
                .isEqualTo("Loan Application View");
        Assertions.assertThat(loanApplicationConfigurationPage.getPageInstructions())
                .as("Loan application view page instructions are not expected")
                .isEqualTo("Select or drag and drop sections to define the Loan application detail page");
        Assertions.assertThat(loanApplicationConfigurationPage.getAvailableSectionsHeading())
                .as("Available sections heading is not expected")
                .isEqualTo("Please choose from available sections");
        Assertions.assertThat(loanApplicationConfigurationPage.getCreateSectionButtonText())
                .as("Create section button text is not expected")
                .isEqualTo("Create Sections");
        Assertions.assertThat(loanApplicationConfigurationPage.getSelectedSectionsHeading())
                .as("Selected sections heading is not expected")
                .isEqualTo("Selected sections");
        Assertions.assertThat(loanApplicationConfigurationPage.getResetButtonText())
                .as("Reset button text is not expected")
                .isEqualTo("Reset");
        Assertions.assertThat(loanApplicationConfigurationPage.isSaveButtonDisplayed())
                .as("Save button is not displayed")
                .isTrue();
        return this;
    }

    public LoanApplicationConfigurationPageSteps selectAnySection() {
        selectedSectionsCountBeforeAddingNewSection = loanApplicationConfigurationPage.getSelectedSectionsCount();
        selectedSectionName = loanApplicationConfigurationPage.selectAnySection();
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Section mapped successfully with the program");
        toastNotification.closeNotification();
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifySectionIsSelected() {
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionSelected(selectedSectionName))
                .as("Section is not selected")
                .isTrue();
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionPresentInAvailableSections(selectedSectionName))
                .as("Section is still present in available sections")
                .isFalse();
        int sequenceNumberForSelectedSection = loanApplicationConfigurationPage.getSequenceNumberForSelectedSection(selectedSectionName);
        Assertions.assertThat(sequenceNumberForSelectedSection)
                .as("Sequence number for selected section is not correct")
                .isEqualTo(selectedSectionsCountBeforeAddingNewSection + 1);
        return this;
    }

    public LoanApplicationConfigurationPageSteps removeSelectedSection() {
        loanApplicationConfigurationPage.removeSelectedSection(selectedSectionName);
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Section removed successfully from the program");
        toastNotification.closeNotification();
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifySectionIsRemoved() {
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionSelected(selectedSectionName))
                .as("Section is not removed")
                .isFalse();
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionPresentInAvailableSections(selectedSectionName))
                .as("Section is not present in available sections")
                .isTrue();
        return this;
    }

    public LoanApplicationConfigurationPageSteps createCustomSection() {
        customSectionName = RandomDataGenerator.generateRandomString(10);
        loanApplicationConfigurationPage
                .openCreateCustomSectionModal()
                .createSection(customSectionName);
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Section created successfully");
        toastNotification.closeNotification();
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifyCustomSectionIsCreated() {
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionSelected(customSectionName))
                .as("Custom section is not selected")
                .isTrue();
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionPresentInAvailableSections(customSectionName))
                .as("Created custom section is present in available sections")
                .isFalse();
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifyExistingSectionNameCannotBeCreated() {
        loanApplicationConfigurationPage.openCreateCustomSectionModal()
                .createSection(customSectionName);
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isErrorNotificationDisplayed()
                .notificationMessageIs("Section name: '%s' is already used for this program.".formatted(customSectionName));
        toastNotification.closeNotification();
        return this;
    }

    public LoanApplicationConfigurationPageSteps updateCustomSectionName() {
        String updatedSectionName = RandomDataGenerator.generateRandomString(10);
        loanApplicationConfigurationPage.openEditCustomSectionModal(customSectionName)
                .createSection(updatedSectionName);
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Section updated successfully");
        toastNotification.closeNotification();
        customSectionName = updatedSectionName;
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifyCustomSectionNameIsUpdated() {
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionSelected(customSectionName))
                .as("Custom section name is not updated")
                .isTrue();
        return this;
    }

    public LoanApplicationConfigurationPageSteps removeCustomSection() {
        loanApplicationConfigurationPage.removeSelectedSection(customSectionName);
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Section removed successfully from the program");
        toastNotification.closeNotification();
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifyCustomSectionIsRemoved() {
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionSelected(customSectionName))
                .as("Custom section is not removed")
                .isFalse();
        Assertions.assertThat(loanApplicationConfigurationPage.isSectionPresentInAvailableSections(customSectionName))
                .as("Custom section is not present in available sections")
                .isTrue();
        return this;
    }

    public LoanApplicationConfigurationPageSteps resetAllSelectedSections() {
        selectedSectionName = loanApplicationConfigurationPage.selectAnySection();
        ToastNotification toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Section mapped successfully with the program");
        toastNotification.closeNotification();

        availableSectionsCountBeforeReset = loanApplicationConfigurationPage.getAvailableSectionsCount();
        selectedSectionsCountBeforeReset = loanApplicationConfigurationPage.getSelectedSectionsCount();
        loanApplicationConfigurationPage.resetSelectedSections();
        toastNotification = new ToastNotification();
        FrameworkAssertions.assertThat(toastNotification)
                .isSuccessNotificationDisplayed()
                .notificationMessageIs("Removed all selected sections from the program");
        toastNotification.closeNotification();
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifyAllSelectedSectionsAreRemoved() {
        loanApplicationConfigurationPage.verifyAllSelectedSectionsAreRemoved();
        int availableSectionsCountAfterReset = loanApplicationConfigurationPage.getAvailableSectionsCount();
        Assertions.assertThat(availableSectionsCountAfterReset)
                .as("Available sections count is not expected")
                .isEqualTo(availableSectionsCountBeforeReset + selectedSectionsCountBeforeReset);
        return this;
    }

    public LoanApplicationConfigurationPageSteps verifySaveButtonIsDisabled() {
        Assertions.assertThat(loanApplicationConfigurationPage.isSaveButtonDisabled())
                .as("Save button is not disabled")
                .isTrue();
        return this;
    }
}
