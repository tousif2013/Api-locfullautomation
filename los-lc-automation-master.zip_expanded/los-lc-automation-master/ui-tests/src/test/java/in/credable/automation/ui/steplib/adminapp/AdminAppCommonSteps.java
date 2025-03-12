package in.credable.automation.ui.steplib.adminapp;

import in.credable.automation.ui.pages.components.HeaderBar;
import in.credable.automation.ui.pages.enums.Language;

public final class AdminAppCommonSteps {
    private AdminAppCommonSteps() {
    }

    public static void changeLanguageToEnglish() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.changeLanguage(Language.ENGLISH);
    }
    
    public static void changeLanguageToHindi() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.changeLanguage(Language.HINDI);
    }
}
