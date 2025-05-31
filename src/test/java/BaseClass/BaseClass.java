package BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.Map;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class BaseClass {
    protected static WebDriver driver;

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

    //quitting drive and flushing extent report
    @AfterTest
    public static void tearDown(){

        driver.quit();
    }
}
