package in.credable.automation.ui.steplib.adminapp.program;

import in.credable.automation.ui.pages.adminapp.program.CreateProgramPage;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;

@Log4j2
public class CreateProgramPageSteps {
    private final CreateProgramPage createProgramPage;

    public CreateProgramPageSteps(CreateProgramPage createProgramPage) {
        this.createProgramPage = createProgramPage;
    }

    public void verifyCreateProgramPageIsOpened() {
        Assertions.assertThat(createProgramPage.isNavigatedToCreateProgramPage())
                .as(() -> "Create program page is not opened")
                .isTrue();
        log.info("Navigated to create program page");
    }
}