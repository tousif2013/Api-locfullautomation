package in.credable.automation.ui.pages.adminapp;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.pages.adminapp.corporate.CorporatePage;
import in.credable.automation.ui.pages.adminapp.dealer.DealerPage;
import in.credable.automation.ui.pages.adminapp.program.ProgramsPage;
import in.credable.automation.ui.pages.adminapp.vendor.VendorPage;
import in.credable.automation.ui.pages.components.SideBar;
import in.credable.automation.ui.utils.SelenideUtils;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class AdminAppDashboardPage {
    private static final SelenideElement DASHBOARD = $(byCssSelector(".app__dashboard"));
    private static final SelenideElement SELECT_PROGRAM = $(byName("program_info"));
    private final SideBar sideBar = new SideBar();

    public boolean isDashboardDisplayed() {
        boolean elementVisible = SelenideUtils.isElementVisible(DASHBOARD);
        log.info("Admin app dashboard is displayed: " + elementVisible);
        return elementVisible;
    }

    public void selectProgram(String programName) {
        log.info("Selecting program: " + programName);
        SELECT_PROGRAM.selectOption(programName);
    }

    public ProgramsPage navigateToProgramPage() {
        log.info("Navigating to program page.");
        return sideBar.navigateToProgramsPage();
    }

    public CorporatePage navigateToCorporatePage() {
        log.info("Navigating to corporate page.");
        return sideBar.navigateToCorporatesPage();
    }

    public DealerPage navigateToDealerPage() {
        log.info("Navigating to dealer page.");
        return sideBar.navigateToDealersPage();
    }

    public VendorPage navigateToVendorPage() {
        log.info("Navigating to vendor page.");
        return sideBar.navigateToVendorsPage();
    }
}
