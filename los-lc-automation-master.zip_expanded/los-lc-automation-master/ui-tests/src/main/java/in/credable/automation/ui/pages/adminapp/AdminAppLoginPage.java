package in.credable.automation.ui.pages.adminapp;

import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.config.ConfigFactory;
import in.credable.automation.ui.utils.SelenideUtils;
import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public class AdminAppLoginPage {
    private static final SelenideElement USERNAME = $("#username");
    private static final SelenideElement PASSWORD = $("#password");
    private static final SelenideElement LOGIN_BUTTON = $(byName("login"));
    private static final SelenideElement OTP_INPUT = $("#otp");
    private static final SelenideElement OTP_SUBMIT_BUTTON = $(byAttribute("value", "Submit OTP"));

    public void open() {
        log.info("Opening login page");
        SelenideUtils.openUrl(ConfigFactory.getEnvironmentConfig().getAdminAppBaseUrl());
    }

    public AdminAppDashboardPage login(String username, String password, String otp) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        enterOTP(otp);
        return submitOTP();
    }

    private void enterUsername(String username) {
        USERNAME.setValue(username);
        log.info("Entered username: {}", username);
    }

    private void enterPassword(String password) {
        PASSWORD.setValue(password);
        log.info("Entered password");
    }

    private void clickLoginButton() {
        LOGIN_BUTTON.click();
        log.info("Clicked login button");
    }

    private void enterOTP(String otp) {
        OTP_INPUT.shouldBe(visible).setValue(otp);
        log.info("Entered OTP: {}", otp);
    }

    private AdminAppDashboardPage submitOTP() {
        OTP_SUBMIT_BUTTON.submit();
        log.info("Submitted OTP");
        return new AdminAppDashboardPage();
    }

}
