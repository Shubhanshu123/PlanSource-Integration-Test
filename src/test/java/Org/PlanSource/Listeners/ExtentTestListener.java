package Org.PlanSource.Listeners;

import Org.PlanSource.Utility.ScreenshotUtil.ScreenshotUtil;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.*;

import java.io.IOException;
import java.util.logging.Logger;

public class ExtentTestListener implements ITestListener {
    protected static ExtentSparkReporter extentSparkReporter;
    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;
    protected static ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();
    private static final Logger logger = Logger.getLogger(ExtentTestListener.class.getName());

    @Override
    public void onStart(ITestContext context) {
        extentSparkReporter  = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-report/extentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        //add content, manage tests etc
        extentSparkReporter.config().setDocumentTitle("Integration test report");
        extentSparkReporter.config().setReportName("Test-Report");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    @Override
    public void onTestStart(ITestResult result) {
        extentTest = extentReports.createTest(result.getMethod().getMethodName());
        extentTestThread.set(extentTest);
        extentTestThread.get().info("Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTestThread.get().log(Status.PASS, "Test Passed! ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestThread.get().log(Status.FAIL,"Test Failed"+ "\n "+result.getThrowable());
        String screenshotPath;
        try {
            screenshotPath = ScreenshotUtil.captureScreenshot( result.getName());
            System.out.println("Screenshot Path: " + screenshotPath);
            extentTestThread.get().fail("Test Failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());

        } catch (IOException e) {
            logger.severe(e.toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result){
        extentTestThread.get().log(Status.SKIP,"Test Skipped"+ "\n "+result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}