package in.credable.automation.testcases;

import in.credable.automation.listener.MethodInterceptor;
import in.credable.automation.listener.SuiteListener;
import in.credable.automation.listener.TestListener;
import org.testng.annotations.Listeners;

@Listeners(value = {SuiteListener.class, TestListener.class, MethodInterceptor.class})
public abstract class BaseTest {
}
