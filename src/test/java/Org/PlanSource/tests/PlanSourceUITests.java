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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

@SuppressWarnings("DataFlowIssue")
@Listeners(Org.PlanSource.Listeners.ExtentTestListener.class)
public class PlanSourceUITests extends BaseClass {

    FluentWait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.ofMillis(200))
            .ignoring(NoSuchElementException.class);

    //login page test
    @Test(priority = 1)
    public void loginToPlanSource() throws IOException {
        driver= Login.login(driver);
    }

    @Test(priority = 2)
    public void createNewEmployeeProfile() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
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
    }

    @Test(priority = 3)
    public void AddMedical_and_VoluntaryBenefits() throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        prop.load(fis);
        driver= Login.login(driver);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//div[@class='loadingoverlay']"))));
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
