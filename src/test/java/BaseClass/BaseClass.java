package BaseClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.Map;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class BaseClass {
    protected static WebDriver driver;
    protected static ExtentSparkReporter extentSparkReporter;
    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;
    protected static ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();

    //extent report setup
    @BeforeTest
    public  static void setupReporter(){
        extentSparkReporter  = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-report/extentReport.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);


        //configuration items to change the look and feel
        //add content, manage tests etc
        extentSparkReporter.config().setDocumentTitle("Integration test report");
        extentSparkReporter.config().setReportName("Test-Report");
        extentSparkReporter.config().setTheme(Theme.STANDARD);
        extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    @BeforeTest
    public static void setUp(){
        chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("autofill.profile_enabled", false); // Disables autofill for addresses
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--no-sandbox");
        options.addArguments("--deny-permission-prompts");
        options.enableBiDi();
        driver= new ChromeDriver(options);
    }
    //getting result into extent report
    @AfterMethod
    public void getResult(ITestResult result) {
        if(result.getStatus() == ITestResult.FAILURE) {
            extentTestThread.get().log(Status.FAIL,result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS) {
            extentTestThread.get().log(Status.PASS, result.getTestName());
        }
        else {
            extentTestThread.get().log(Status.SKIP, result.getTestName());
        }
    }
    //quitting drive and flushing extent report
    @AfterTest
    public static void tearDown(){
        extentReports.flush();
        driver.quit();
    }
}
