package in.credable.automation.ui.pages.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
public enum Language {
    ENGLISH("English", "en"),
    HINDI("हिंदी", "hi");

    private final String languageName;
    private final String languageCodeIso2;

    Language(String languageName, String languageCodeIso2) {
        this.languageName = languageName;
        this.languageCodeIso2 = languageCodeIso2;
    }

    public static Language decode(String languageName) {
        return Arrays.stream(Language.values())
                .filter(language -> StringUtils.equals(language.getLanguageName(), languageName))
                .findFirst()
                .orElseThrow();
    }
}
