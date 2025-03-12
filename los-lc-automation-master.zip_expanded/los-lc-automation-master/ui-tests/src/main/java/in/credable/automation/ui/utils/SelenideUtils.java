package in.credable.automation.ui.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import in.credable.automation.ui.webdriver.WebDriverConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

@Log4j2
public final class SelenideUtils {

    private SelenideUtils() {
    }

    public static void openUrl(String url) {
        Selenide.open(url);
        log.debug("Opened URL: {}", url);
    }

    /**
     * Waits for URL to be ended with specified suffix
     *
     * @param urlSuffix The URL suffix to wait for
     * @return True if URL is ended with specified suffix, otherwise false
     */
    public static boolean isUrlEndsWith(String urlSuffix) {
        try {
            Selenide.Wait().until(ExpectedConditions.urlMatches(".*%s$".formatted(urlSuffix)));
            log.debug("Waited for URL to be ended with: {}", urlSuffix);
            return true;
        } catch (Exception e) {
            log.error("URL is not ended with: " + urlSuffix, e);
            return false;
        }
    }

    public static void goBack() {
        Selenide.back();
        log.debug("Navigated back");
    }

    public static boolean isElementVisible(SelenideElement element) {
        return isElementVisible(element, Duration.ofMillis(Configuration.timeout));
    }

    public static boolean isElementVisible(SelenideElement element, Duration timeoutDuration) {
        boolean isElementVisible = element.is(Condition.visible, timeoutDuration);
        if (isElementVisible) {
            log.info("Element: {} is visible", element);
        } else {
            log.warn("Element: {} is not visible", element);
        }
        return isElementVisible;
    }

    public static boolean isElementDisabled(SelenideElement element) {
        String[] classNames = StringUtils.split(element.getAttribute("class"));
        return ArrayUtils.containsAny(classNames, "disable", "disabled");
    }

    public static boolean isRunningOnChrome() {
        return StringUtils.equals(Configuration.browser, "chrome");
    }

    public static void setChromeCapabilities() {
        Configuration.browserCapabilities = WebDriverConfig.getChromeOptions();
    }
}
