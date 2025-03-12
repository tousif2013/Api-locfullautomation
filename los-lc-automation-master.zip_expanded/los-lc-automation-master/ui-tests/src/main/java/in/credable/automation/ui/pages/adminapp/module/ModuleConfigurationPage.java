package in.credable.automation.ui.pages.adminapp.module;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.utils.SelenideUtils;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

public class ModuleConfigurationPage {
    private static final SelenideElement MODULE_CONFIGURATION_CONTAINER = $(byCssSelector(".module_configuration_container"));

    public boolean isModuleConfigurationPageDisplayed() {
        return SelenideUtils.isElementVisible(MODULE_CONFIGURATION_CONTAINER);
    }
}
