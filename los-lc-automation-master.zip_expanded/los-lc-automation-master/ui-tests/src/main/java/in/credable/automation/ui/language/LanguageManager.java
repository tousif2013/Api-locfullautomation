package in.credable.automation.ui.language;

import in.credable.automation.ui.pages.enums.Language;

public final class LanguageManager {
    private static final ThreadLocal<Language> SELECTED_LANGUAGE = new ThreadLocal<>();

    public static Language getSelectedLanguage() {
        return SELECTED_LANGUAGE.get();
    }

    public static void setSelectedLanguage(Language language) {
        SELECTED_LANGUAGE.set(language);
    }

}
