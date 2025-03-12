package in.credable.automation.ui.listener;

import in.credable.automation.ui.utils.SelenideUtils;
import in.credable.automation.utils.LoggerUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.testng.ISuite;
import org.testng.ISuiteListener;

@Log4j2
public class SuiteListener implements ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        LoggerUtil.printLog(log,
                Level.INFO,
                "Started Test Suite :: {} on ENV: {}",
                suite.getName(),
                System.getProperty("env", System.getenv("env")));

        if (SelenideUtils.isRunningOnChrome()) {
            SelenideUtils.setChromeCapabilities();
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        LoggerUtil.printLog(log, Level.INFO, "Test suite finished :: {}", suite.getName());
    }
}
