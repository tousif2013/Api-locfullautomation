package in.credable.automation.ui.steplib.adminapp.program;

import in.credable.automation.ui.pages.adminapp.program.CreateProgramPage;
import in.credable.automation.ui.pages.adminapp.program.ProgramsPage;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;

@Log4j2
public class ProgramPageSteps {
    private final ProgramsPage programsPage;

    public ProgramPageSteps(ProgramsPage programsPage) {
        this.programsPage = programsPage;
    }

    public ProgramPageSteps verifyProgramPageIsOpened() {
        Assertions.assertThat(programsPage.isNavigatedToProgramPage())
                .as(() -> "Programs page is not opened")
                .isTrue();
        log.info("Navigated to programs page");
        return this;
    }

    public CreateProgramPageSteps navigateToCreateProgramPage() {
        CreateProgramPage createProgramPage = programsPage.navigateToCreateProgramPage();
        return new CreateProgramPageSteps(createProgramPage);
    }
}
