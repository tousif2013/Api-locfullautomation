package in.credable.automation.ui.language;

import in.credable.automation.ui.pages.enums.Language;
import lombok.extern.log4j.Log4j2;

import java.util.EnumMap;
import java.util.Map;

@Log4j2
public final class Translator {
    private static final Map<Language, TranslationCache> TRANSLATION_CACHE = new EnumMap<>(Language.class);

    private Translator() {
    }

    public static String getLabelValue(String labelKey) {
        Language selectedLanguage = LanguageManager.getSelectedLanguage();
        String labelValue = getTranslationsFor(selectedLanguage).getTranslation(labelKey);
        log.debug("Label value for key {} is {}", labelKey, labelValue);
        return labelValue;
    }

    private static TranslationCache getTranslationsFor(Language language) {
        return TRANSLATION_CACHE.computeIfAbsent(language, TranslationCache::createTranslationCacheFor);
    }
}
