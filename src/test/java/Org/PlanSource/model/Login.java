package Org.PlanSource.model;

import Org.PlanSource.Listeners.ExtentTestListener;
import Org.PlanSource.PageObject.PlanSourceLoginPage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

public class Login extends ExtentTestListener {
    private static final Logger logger = Logger.getLogger(Login.class.getName());
    private static final SoftAssert sa =new SoftAssert();

    public static WebDriver login(WebDriver driver) throws IOException {

        Properties prop;
        FileInputStream fis=null;
        try {
            prop = new Properties();
            fis = new FileInputStream("src/test/resources/config.properties");
            PlanSourceLoginPage login = new PlanSourceLoginPage();
            prop.load(fis);
            driver.get("https://partner-dev-benefits.plansource.com/");
            driver.manage().window().maximize();
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            login.setUsername(driver.findElement(By.id("user_name")));
            explicitWait.until(ExpectedConditions.visibilityOf(login.getUsername()));
            login.getUsername().sendKeys(prop.getProperty("username"));

            login.setPassword(driver.findElement(By.id("password")));
            login.getPassword().sendKeys(prop.getProperty("password"));

            login.setSubmit(driver.findElement(By.id("logon_submit")));
            login.getSubmit().click();

            explicitWait.until(ExpectedConditions.titleIs("Dashboard"));
            sa.assertEquals("Dashboard",driver.getTitle());
            extentTestThread.get().log(Status.PASS, "Page Title 'Employee Profile' matched");

        } catch (IOException | ElementNotInteractableException e) {
            logger.severe(e.toString());
        }
        finally {
            Assert.assertNotNull(fis);
            fis.close();

        }
        return driver;
    }
}
