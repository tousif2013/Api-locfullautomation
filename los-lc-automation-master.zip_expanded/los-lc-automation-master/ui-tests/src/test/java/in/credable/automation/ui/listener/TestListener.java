package in.credable.automation.ui.listener;

import in.credable.automation.utils.LoggerUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

@Log4j2
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        ThreadContext.put("testName", testName);
        String description = result.getMethod().getDescription();
        if (StringUtils.isNotBlank(description)) {
            LoggerUtil.printLog(log, Level.INFO, "Starting test :: {}", description);
        } else {
            LoggerUtil.printLog(log, Level.INFO, "Starting test :: {}", testName);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        LoggerUtil.printLog(log, Level.INFO, "Test passed:: {}", testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        LoggerUtil.printLog(log, Level.ERROR, "Test failed:: {}", testName);
        log.error("Reason for failure: ", result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        LoggerUtil.printLog(log, Level.WARN, "Test skipped:: {}", testName);
    }
}
