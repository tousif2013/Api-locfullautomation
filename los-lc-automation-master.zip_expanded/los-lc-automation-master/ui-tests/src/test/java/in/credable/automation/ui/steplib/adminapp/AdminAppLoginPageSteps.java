package in.credable.automation.ui.steplib.adminapp;

import in.credable.automation.ui.config.ConfigFactory;
import in.credable.automation.ui.config.EnvironmentConfig;
import in.credable.automation.ui.language.LanguageManager;
import in.credable.automation.ui.pages.adminapp.AdminAppDashboardPage;
import in.credable.automation.ui.pages.adminapp.AdminAppLoginPage;
import in.credable.automation.ui.pages.components.HeaderBar;
import in.credable.automation.ui.pages.enums.Language;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class AdminAppLoginPageSteps {
    private static final EnvironmentConfig ENVIRONMENT_CONFIG = ConfigFactory.getEnvironmentConfig();
    public static final String OTP = ENVIRONMENT_CONFIG.getAdminAppOtp();
    public static final String PASSWORD = ENVIRONMENT_CONFIG.getAdminAppPassword();
    public static final String USERNAME = ENVIRONMENT_CONFIG.getAdminAppUsername();
    private final AdminAppLoginPage adminAppLoginPage;

    private AdminAppLoginPageSteps() {
        adminAppLoginPage = new AdminAppLoginPage();
    }

    public static AdminAppLoginPageSteps openAdminApp() {
        log.info("Opening Admin App");
        AdminAppLoginPageSteps adminAppLoginPageSteps = new AdminAppLoginPageSteps();
        adminAppLoginPageSteps.adminAppLoginPage.open();
        return adminAppLoginPageSteps;
    }

    public AdminAppDashboardPageSteps loginToAdminApp() {
        AdminAppDashboardPage adminAppDashboardPage = new AdminAppDashboardPage();
        if (adminAppDashboardPage.isDashboardDisplayed()) {
            log.info("User is already logged into admin app");
        } else {
            log.info("Logging in to Admin App");
            adminAppDashboardPage = adminAppLoginPage.login(USERNAME, PASSWORD, OTP);
        }
        setSelectedLanguage();
        return new AdminAppDashboardPageSteps(adminAppDashboardPage);
    }

    private void setSelectedLanguage() {
        HeaderBar headerBar = new HeaderBar();
        Language selectedLanguage = headerBar.getSelectedLanguage();
        LanguageManager.setSelectedLanguage(selectedLanguage);
    }
}
