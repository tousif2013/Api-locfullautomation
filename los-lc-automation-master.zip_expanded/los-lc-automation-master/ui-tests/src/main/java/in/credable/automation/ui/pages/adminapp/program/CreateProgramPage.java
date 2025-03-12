package in.credable.automation.ui.pages.adminapp.program;

import in.credable.automation.ui.utils.SelenideUtils;

public class CreateProgramPage {

    public boolean isNavigatedToCreateProgramPage() {
        return SelenideUtils.isUrlEndsWith("/create");
    }
}
