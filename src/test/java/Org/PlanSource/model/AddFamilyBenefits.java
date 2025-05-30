package Org.PlanSource.model;

import Org.PlanSource.PageObject.FamilyBenefitsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class AddFamilyBenefits {

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
            benefits.setGetStartedButton(driver.findElement(By.id("enrollmentStepOne")));
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getGetStartedButton())).click();

            benefits.setNextButton(driver.findElement(By.id("submit_form")));
            wait.until(ExpectedConditions.elementToBeClickable(benefits.getNextButton())).click();
            wait.until(ExpectedConditions.titleIs("Dependents - Testing Plansource Client Benefits"));
            Assert.assertEquals(driver.getTitle(),"Dependents - Testing Plansource Client Benefits");
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
            benefits.setSaveButton(driver.findElement(By.id("submit_form")));
            benefits.getSaveButton().click();
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
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Shop Plans")));
            List<WebElement> shopPlansButtons= driver.findElements(By.linkText("Shop Plans"));
            benefits.setUpdatecartButton(shopPlansButtons.getFirst());
            benefits.getUpdatecartButton().click();
            Thread.sleep(3000);
            benefits.setUpdatecartButton(wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("updateCartBtn")))));
            benefits.getUpdatecartButton().click();
            Thread.sleep(6000);
            benefits.setRadioButton(wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"survey_form\"]/div[2]/div/div[2]/div[1]/div/div/fieldset/div[2]/div/div/div/label[2]/span")))));
            benefits.getRadioButton().click();
            driver.findElement(By.id("next")).click();

            benefits.setSaveButton(wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("submit_form")))));
            benefits.getSaveButton().click();
            Thread.sleep(6000);
            wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//* [contains(., 'To') and contains(., 'Benefits')]")))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Shop Plans")));
            shopPlansButtons= driver.findElements(By.linkText("Shop Plans"));
            benefits.setUpdatecartButton(shopPlansButtons.get(5));
            benefits.getUpdatecartButton().click();



        }
        catch (Exception e){
            logger.severe(e.toString());
            Assert.fail();
        }
        return driver;
    }
}
