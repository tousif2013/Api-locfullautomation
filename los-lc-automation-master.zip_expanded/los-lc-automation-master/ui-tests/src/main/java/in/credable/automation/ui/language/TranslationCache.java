package in.credable.automation.ui.language;

import in.credable.automation.service.language.LanguageService;
import in.credable.automation.ui.pages.enums.Language;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public final class TranslationCache {
    private final Map<String, String> translationCacheMap = new HashMap<>();

    private TranslationCache() {
    }

    static TranslationCache createTranslationCacheFor(Language language) {
        log.debug("Creating translation cache for language {}", language);
        TranslationCache translationCache = new TranslationCache();
        translationCache.translationCacheMap.putAll(LanguageService.getLanguageMap(language.getLanguageCodeIso2()));
        log.debug("Translation cache created for language {}", language);
        return translationCache;
    }

    public String getTranslation(String key) {
        return translationCacheMap.get(key);
    }

}
