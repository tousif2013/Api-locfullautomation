package in.credable.automation.ui.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.language.LabelKey;
import in.credable.automation.ui.language.Translator;
import in.credable.automation.ui.pages.adminapp.corporate.CorporatePage;
import in.credable.automation.ui.pages.adminapp.dealer.DealerPage;
import in.credable.automation.ui.pages.adminapp.program.ProgramsPage;
import in.credable.automation.ui.pages.adminapp.vendor.VendorPage;

import java.util.function.Function;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selenide.$;

public final class SideBar {
    private static final SelenideElement SIDE_BAR_CONTAINER = $(byCssSelector("div.side__bar"));
    private static final Function<String, SelenideElement> SIDE_BAR_LINK = menuItem ->
            SIDE_BAR_CONTAINER.$$(byCssSelector("div.menu_item_text")).findBy(Condition.exactTextCaseSensitive(menuItem));

    public ProgramsPage navigateToProgramsPage() {
        navigateTo(LabelKey.MENU_PROGRAMS);
        return new ProgramsPage();
    }

    public CorporatePage navigateToCorporatesPage() {
        navigateTo(LabelKey.MENU_CORPORATES);
        return new CorporatePage();
    }

    public DealerPage navigateToDealersPage() {
        navigateTo(LabelKey.MENU_DEALERS);
        return new DealerPage();
    }

    public VendorPage navigateToVendorsPage() {
        navigateTo(LabelKey.MENU_VENDORS);
        return new VendorPage();
    }

    private void navigateTo(String labelKey) {
        String menuText = Translator.getLabelValue(labelKey);
        SelenideElement sidebarLink = SIDE_BAR_LINK.apply(menuText);
        sidebarLink.click();
        sidebarLink.parent().shouldHave(Condition.cssClass("active"));
        AppLoader.waitForLoader();
    }
}
