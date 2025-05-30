package Org.PlanSource.tests;

import BaseClass.BaseClass;
import Org.PlanSource.model.AddNewEmployee;
import Org.PlanSource.model.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@SuppressWarnings("DataFlowIssue")
public class PlanSourceUITests extends BaseClass {

    private static final SoftAssert sa = new SoftAssert();
    private final WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
    //login page test
    @Test
    public void loginToPlanSource() throws IOException {
        extentTest =extentReports.createTest(new Exception().getStackTrace()[0].getMethodName(),"Login to PlanSource");
        extentTestThread.set(extentTest);
        driver=LoginPage.login(driver);
        sa.assertEquals("Dashboard",driver.getTitle());

    }
    @Test
    public void createNewEmployeeProfile() throws IOException {
        extentTest =extentReports.createTest(new Exception().getStackTrace()[0].getMethodName(),"Add New Employee Details");
        extentTestThread.set(extentTest);
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/cred.properties");
        prop.load(fis);
        driver=LoginPage.login(driver);
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

}
