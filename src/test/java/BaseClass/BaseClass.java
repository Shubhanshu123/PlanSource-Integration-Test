package BaseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class BaseClass {
    protected static WebDriver driver;
    @BeforeTest
    public static void setUp(){
        chromedriver().setup();
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.enableBiDi();
        driver= new ChromeDriver(options);
    }

    @AfterTest
    public static void tearDown(){
        driver.quit();
    }
}
