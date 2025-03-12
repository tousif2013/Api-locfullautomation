package in.credable.automation.ui.pages.adminapp.program;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.pages.components.AppLoader;
import in.credable.automation.ui.utils.SelenideUtils;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

public class ProgramsPage {
    private static final SelenideElement PROGRAMS_PAGE_CONTAINER = $(byCssSelector("div.dealers__info"));
    private static final SelenideElement PROGRAMS_PAGE_HEADER = PROGRAMS_PAGE_CONTAINER.$(byCssSelector("div.dealer__header"));
    private static final SelenideElement CREATE_PROGRAM_BUTTON = PROGRAMS_PAGE_HEADER.$(byCssSelector("div.dealer__upload__button"));

    public boolean isNavigatedToProgramPage() {
        return SelenideUtils.isUrlEndsWith("/programs");
    }

    public CreateProgramPage navigateToCreateProgramPage() {
        CREATE_PROGRAM_BUTTON.shouldBe(visible).click();
        AppLoader.waitForLoader();
        return new CreateProgramPage();
    }
}
