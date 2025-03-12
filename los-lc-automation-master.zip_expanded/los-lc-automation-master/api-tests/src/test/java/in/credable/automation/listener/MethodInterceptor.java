package in.credable.automation.listener;

import in.credable.automation.utils.LoggerUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class MethodInterceptor implements IMethodInterceptor {

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        List<IMethodInstance> result = new ArrayList<>();
        String currentXmlTestName = context.getCurrentXmlTest().getName();
        LoggerUtil.printLog(log, Level.DEBUG, "Current Xml Test Name: {}", currentXmlTestName);
        String testNamesPropertyValue = System.getProperty("testNames");
        if (StringUtils.isBlank(testNamesPropertyValue)) {
            result.addAll(methods);
        } else {
            String[] testNames = StringUtils.split(testNamesPropertyValue, ",");
            if (Arrays.stream(testNames).anyMatch(testName -> StringUtils.equals(currentXmlTestName, testName))) {
                result.addAll(methods);
            }
        }
        return result;
    }
}
