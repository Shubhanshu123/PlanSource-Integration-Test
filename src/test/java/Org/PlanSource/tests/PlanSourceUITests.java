package Org.PlanSource.tests;

import BaseClass.BaseClass;
import Org.PlanSource.model.AddFamilyBenefits;
import Org.PlanSource.model.AddNewEmployee;
import Org.PlanSource.model.Login;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@SuppressWarnings("DataFlowIssue")
public class PlanSourceUITests extends BaseClass {

    private static final SoftAssert sa = new SoftAssert();
    FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(NoSuchElementException.class);
    //private final WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));
    //login page test
    @Test
    public void loginToPlanSource() throws IOException {
        extentTest =extentReports.createTest(new Exception().getStackTrace()[0].getMethodName(),"Login to PlanSource");
        extentTestThread.set(extentTest);
        driver= Login.login(driver);
        sa.assertEquals("Dashboard",driver.getTitle());

    }
    @Test
    public void createNewEmployeeProfile() throws IOException {
        extentTest =extentReports.createTest(new Exception().getStackTrace()[0].getMethodName(),"Add New Employee Details");
        extentTestThread.set(extentTest);
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/cred.properties");
        prop.load(fis);
        driver= Login.login(driver);
        if(!driver.findElements(By.linkText(prop.getProperty("firstname") + " " + prop.getProperty("lastname"))).isEmpty()){
            WebElement linktext= driver.findElement(By.linkText(prop.getProperty("firstname")+" "+prop.getProperty("lastname")));
            wait.until(ExpectedConditions.elementToBeClickable(linktext));
            linktext.click();
        }
        else {
            driver = AddNewEmployee.addDetails(driver);
        }
        sa.assertEquals("Employee Profile", driver.getTitle());

    }
    @Test
    public void AddMedical_and_VoluntaryBenefits() throws IOException {
        extentTest =extentReports.createTest(new Exception().getStackTrace()[0].getMethodName(),"Add Medical and Voluntary Benefits to employee profile");
        extentTestThread.set(extentTest);
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/cred.properties");
        prop.load(fis);
        driver= Login.login(driver);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class=\"loadingoverlay\"]"))));
        if(!driver.findElements(By.linkText(prop.getProperty("firstname") + " " + prop.getProperty("lastname"))).isEmpty()){
            WebElement linktext= driver.findElement(By.linkText(prop.getProperty("firstname")+" "+prop.getProperty("lastname")));
            wait.until(ExpectedConditions.elementToBeClickable(linktext));
            linktext.click();
        }
        else {
            driver = AddNewEmployee.addDetails(driver);
        }
        driver=AddFamilyBenefits.addFamilyDetails(driver);
        driver=AddFamilyBenefits.addBenefits(driver);
    }

}
