package in.credable.automation.ui.pages.components;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.language.LanguageManager;
import in.credable.automation.ui.pages.enums.Language;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public final class HeaderBar {
    private static final SelenideElement HEADER_BAR = $(byCssSelector("div.header__bar"));
    private static final SelenideElement LANGUAGE_CHANGE = HEADER_BAR.$(byCssSelector("div.language_change"));
    private static final Function<String, SelenideElement> LANGUAGE_BY_TEXT = languageName ->
            LANGUAGE_CHANGE.$(byTagAndText("div", languageName));
    private static final SelenideElement SELECTED_LANGUAGE = LANGUAGE_CHANGE.$(byCssSelector(".select"));
    public static final ElementsCollection LANGUAGES = LANGUAGE_CHANGE.$$("div.language");

    public void changeLanguage(Language language) {
        log.info("Changing language to: {}", language.getLanguageName());
        LANGUAGE_BY_TEXT.apply(language.getLanguageName()).click();
        AppLoader.waitForLoader();
        LANGUAGE_BY_TEXT.apply(language.getLanguageName()).shouldHave(Condition.cssClass("select"));
        log.info("Changed language to: {}", language.getLanguageName());
        LanguageManager.setSelectedLanguage(language);
    }

    public Language getSelectedLanguage() {
        String selectedLanguageText;
        if (SELECTED_LANGUAGE.isDisplayed()) {
            selectedLanguageText = SELECTED_LANGUAGE.getText();
        } else {
            selectedLanguageText = LANGUAGES.shouldBe(CollectionCondition.sizeGreaterThan(0)).first().getText();
        }
        log.info("Selected language is: {}", selectedLanguageText);
        return Language.decode(StringUtils.trimToEmpty(selectedLanguageText));
    }
}
