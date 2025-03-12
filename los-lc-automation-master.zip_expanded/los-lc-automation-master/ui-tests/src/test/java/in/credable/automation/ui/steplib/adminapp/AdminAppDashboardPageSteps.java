package in.credable.automation.ui.steplib.adminapp;

import in.credable.automation.service.program.ProgramService;
import in.credable.automation.service.vo.program.ProgramVO;
import in.credable.automation.ui.config.ConfigFactory;
import in.credable.automation.ui.pages.adminapp.AdminAppDashboardPage;
import in.credable.automation.ui.pages.adminapp.corporate.CorporatePage;
import in.credable.automation.ui.pages.adminapp.dealer.DealerPage;
import in.credable.automation.ui.pages.adminapp.program.ProgramsPage;
import in.credable.automation.ui.pages.adminapp.vendor.VendorPage;
import in.credable.automation.ui.steplib.adminapp.dealer.DealerPageSteps;
import in.credable.automation.ui.steplib.adminapp.program.ProgramPageSteps;
import in.credable.automation.ui.steplib.adminapp.vendor.VendorPageSteps;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;

@Log4j2
public class AdminAppDashboardPageSteps {
    private final AdminAppDashboardPage adminAppDashboardPage;

    public AdminAppDashboardPageSteps(AdminAppDashboardPage adminAppDashboardPage) {
        this.adminAppDashboardPage = adminAppDashboardPage;
    }

    public AdminAppDashboardPageSteps verifyAdminAppDashboardPageIsOpened() {
        Assertions.assertThat(adminAppDashboardPage.isDashboardDisplayed())
                .as(() -> "Dashboard is not displayed")
                .isTrue();
        log.info("Admin App dashboard page is opened");
        return this;
    }

    public AdminAppDashboardPageSteps selectProgramForDealerAutoApproved() {
        Long programId = ConfigFactory.getEnvironmentConfig().getDealerAutoApprovedProgramId();
        String programName = ProgramService.getProgramById(programId).as(ProgramVO.class).getProgramName();
        adminAppDashboardPage.selectProgram(programName);
        log.info("Selected program for dealer auto approved");
        return this;
    }

    public AdminAppDashboardPageSteps selectProgramForVendorAutoApproved() {
        Long programId = ConfigFactory.getEnvironmentConfig().getVendorAutoApprovedProgramId();
        String programName = ProgramService.getProgramById(programId).as(ProgramVO.class).getProgramName();
        adminAppDashboardPage.selectProgram(programName);
        log.info("Selected program for vendor auto approved");
        return this;
    }

    public ProgramPageSteps navigateToProgramPage() {
        ProgramsPage programsPage = adminAppDashboardPage.navigateToProgramPage();
        return new ProgramPageSteps(programsPage);
    }

    public CorporatePageSteps navigateToCorporatePage() {
        CorporatePage corporatePage = adminAppDashboardPage.navigateToCorporatePage();
        return new CorporatePageSteps(corporatePage);
    }

    public DealerPageSteps navigateToDealerPage() {
        DealerPage dealerPage = adminAppDashboardPage.navigateToDealerPage();
        return new DealerPageSteps(dealerPage);
    }

    public VendorPageSteps navigateToVendorPage() {
        VendorPage vendorPage = adminAppDashboardPage.navigateToVendorPage();
        return new VendorPageSteps(vendorPage);
    }
}
