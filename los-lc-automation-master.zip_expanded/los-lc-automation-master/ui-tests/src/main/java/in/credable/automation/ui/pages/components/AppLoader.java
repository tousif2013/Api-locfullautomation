package in.credable.automation.ui.pages.components;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

@Log4j2
public final class AppLoader {
    private static final String LOADING_ICON_XPATH = "//div[@id='loadingPage']/div[@class='loading__icon']";

    public static void waitForLoader() {
        log.info("Waiting for loader");
        $(byXpath(LOADING_ICON_XPATH)).shouldBe(hidden);
        log.info("Waited for loader");
    }
}
