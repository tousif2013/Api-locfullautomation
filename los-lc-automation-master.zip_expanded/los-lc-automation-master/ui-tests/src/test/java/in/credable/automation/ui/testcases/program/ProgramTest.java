package in.credable.automation.ui.testcases.program;

import in.credable.automation.ui.steplib.adminapp.AdminAppLoginPageSteps;
import in.credable.automation.ui.steplib.adminapp.program.ProgramPageSteps;
import in.credable.automation.ui.testcases.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ProgramTest extends BaseTest {

    private ProgramPageSteps programPageSteps;

    @BeforeClass
    public void loginToAdminApp() {
        programPageSteps = AdminAppLoginPageSteps.openAdminApp()
                .loginToAdminApp()
                .navigateToProgramPage()
                .verifyProgramPageIsOpened();
    }

    @Test(description = "Test #558 - Verify that user can navigate to program module by clicking on Create Program.")
    public void verifyNavigatingToCreateProgramPageByClickingOnCreateProgramButton() {
        programPageSteps.navigateToCreateProgramPage()
                .verifyCreateProgramPageIsOpened();
    }
}
