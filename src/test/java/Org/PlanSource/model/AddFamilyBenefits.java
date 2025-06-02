package Org.PlanSource.model;

import Org.PlanSource.Listeners.ExtentTestListener;
import Org.PlanSource.PageObject.FamilyBenefitsPage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;



public class AddFamilyBenefits extends ExtentTestListener {

    private static final Logger logger = Logger.getLogger(AddFamilyBenefits.class.getName());

    public static WebDriver addFamilyDetails(WebDriver driver){
        FamilyBenefitsPage benefits= new FamilyBenefitsPage();
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(ElementClickInterceptedException.class);
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("New Hire Enrollment")));
            benefits.setEnrollmentLink(driver.findElement(By.linkText("New Hire Enrollment")));
            benefits.getEnrollmentLink().click();
            Thread.sleep(3000);
            extentTestThread.get().log(Status.PASS, "New hire enrollment clicked");
            benefits.setGetStartedButton(driver.findElement(By.id("enrollmentStepOne")));
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getGetStartedButton())).click();
            extentTestThread.get().log(Status.PASS, "get started button click successful");
            benefits.setNextButton(driver.findElement(By.id("submit_form")));
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getNextButton())).click();
            wait.until(ExpectedConditions.titleIs("Dependents - Testing Plansource Client Benefits"));
            Assert.assertEquals(driver.getTitle(),"Dependents - Testing Plansource Client Benefits");
            extentTestThread.get().log(Status.PASS, "Page Title "+ driver.getTitle() +" matched");
            benefits.setAddFamilyMember(driver.findElement(By.id("AddDependentDiv")));
            benefits.getAddFamilyMember().click();
            benefits.setFirstname(driver.findElement(By.id("first_name")));
            benefits.getFirstname().sendKeys("Rakesh");
            benefits.setLastname(driver.findElement(By.id("last_name")));
            benefits.getLastname().sendKeys("Sharma");
            benefits.setGender(driver.findElement(By.id("gender")));
            Select dropdown= new Select(benefits.getGender());
            dropdown.selectByIndex(1);
            benefits.setBirthDate(driver.findElement(By.id("birthdate")));
            benefits.getBirthDate().sendKeys("08/14/1996");
            benefits.setRelationship(driver.findElement(By.id("relationship")));
            dropdown=new Select(benefits.getRelationship());
            dropdown.selectByValue("spouse");
            extentTestThread.get().log(Status.PASS, "dependent details were added");
            benefits.setSaveButton(driver.findElement(By.id("submit_form")));
            benefits.getSaveButton().click();
            extentTestThread.get().log(Status.PASS, "form submitted");
        }
        catch (Exception e){
            logger.severe(e.toString());
            Assert.fail();
        }

        return driver;
    }
    public static WebDriver addBenefits(WebDriver driver){
        FamilyBenefitsPage benefits= new FamilyBenefitsPage();
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(200))
                .ignoring(ElementClickInterceptedException.class);
        try {
            Thread.sleep(3000);
            benefits.setNextButton(driver.findElement(By.id("submit_form")));
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getNextButton())).click();
            extentTestThread.get().log(Status.PASS, "reached benefits page");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Shop Plans")));
            List<WebElement> shopPlansButtons= driver.findElements(By.linkText("Shop Plans"));
            benefits.setUpdatecartButton(shopPlansButtons.getFirst());
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getUpdatecartButton())).click();
            Thread.sleep(3000);
            extentTestThread.get().log(Status.PASS, "Health benefit page accessed");
            benefits.setUpdatecartButton(wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("updateCartBtn")))));
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getUpdatecartButton())).click();
            extentTestThread.get().log(Status.PASS, "Health benefit added");
            Thread.sleep(6000);
            benefits.setRadioButton(wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"survey_form\"]/div[2]/div/div[2]/div[1]/div/div/fieldset/div[2]/div/div/div/label[2]/span")))));
            benefits.getRadioButton().click();
            driver.findElement(By.id("next")).click();
            benefits.setSaveButton(wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("submit_form")))));
            benefits.getSaveButton().click();
            extentTestThread.get().log(Status.PASS, "Survey filled successfully");
            Thread.sleep(6000);
            JavascriptExecutor jse= (JavascriptExecutor) driver;
            jse.executeScript("document.querySelector('#app > section > section > div > div > main > div:nth-child(3) > div > div > div._3fMN2gLIuf3kBXgZYnYIIC.container-fluid.m-b-md.undefined > a').click()");
            Thread.sleep(3000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Shop Plans")));
            Actions action = new Actions(driver);
            shopPlansButtons= driver.findElements(By.linkText("Shop Plans"));
            benefits.setUpdatecartButton(shopPlansButtons.get(5));
            action.scrollToElement(shopPlansButtons.get(6)).perform();
            benefits.getUpdatecartButton().click();
            Thread.sleep(3000);
            Select dropdown = new Select(driver.findElement(By.id("volumeSelect")));
            dropdown.selectByIndex(1);
            benefits.setUpdatecartButton(driver.findElement(By.id("updateCartBtn")));
            benefits.getUpdatecartButton().click();
            extentTestThread.get().log(Status.PASS, "Voulantary benefits added to cart");
            Thread.sleep(3000);
            jse.executeScript("document.querySelector('#plan_popup > div > div > div.modal-footer.text-left > button').click()");
            Thread.sleep(6000);
            jse.executeScript("document.querySelector('#app > section > section > div > div > main > div:nth-child(3) > div > div > div._3fMN2gLIuf3kBXgZYnYIIC.container-fluid.m-b-md.undefined > a').click()");
            Thread.sleep(3000);
            extentTestThread.get().log(Status.PASS, "waiting to trigger API call for dental benefits");
        }
        catch (Exception | AssertionError e){
            logger.severe(e.toString());
            Assert.fail(e.toString());
        }
        return driver;
    }
}
