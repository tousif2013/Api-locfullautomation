package in.credable.automation.ui.testcases;

import com.codeborne.selenide.testng.TextReport;
import in.credable.automation.ui.listener.MethodInterceptor;
import in.credable.automation.ui.listener.SuiteListener;
import in.credable.automation.ui.listener.TestListener;
import org.testng.annotations.Listeners;

@Listeners({TextReport.class, SuiteListener.class, TestListener.class, MethodInterceptor.class})
public abstract class BaseTest {

}
