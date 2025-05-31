package Org.PlanSource.model;

import Org.PlanSource.Listeners.ExtentTestListener;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.logging.Logger;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DentalBenefitAddAPIandUI extends ExtentTestListener {

    private static final Logger logger = Logger.getLogger(DentalBenefitAddAPIandUI.class.getName());
    private final SoftAssert sa =new SoftAssert();

    public WebDriver dentalBenefit(WebDriver driver){
        final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
        try {
            Cookie driverCookie = driver.manage().getCookieNamed("_session_id");
            Assert.assertNotNull(driverCookie);
            extentTestThread.get().log(Status.PASS, "Session Cookie is not Empty. ID = "+ driverCookie.getValue());
            var URI = "https://partner-dev-benefits.plansource.com/v1/self_service/coverages";
            String jsonBody = """
            {
                "election": {
                    "coverage_level_id": 137,
                    "dependent_ids": [],
                    "org_plan_id": 319901188
                },
                "enrollment_context_type": "initial",
                "include_benefits_in_response": true,
                "include_related_coverage_changes": true,
                "life_event_completed": false,
                "org_benefit_id": 121137668
            }
            """;
            Response response = given()
                    .baseUri(URI)
                    .cookie("_session_id",driverCookie.getValue())
                    .headers("Referer", "https://partner-dev-benefits.plansource.com/subscriber/benefit/121137668")
                    .headers("Content-Type", "application/json")
                    .body(jsonBody)
                    .when().put();
            extentTestThread.get().info("Status code of API call is " + response.getStatusCode());
            //assert on status code
            response.then().log().body().assertThat().statusCode(200);
            extentTestThread.get().log(Status.PASS, "Status code matched as 200");
            var checkResponse =response.then().log().body().assertThat().body("status", equalTo("success"));
            extentTestThread.get().info(checkResponse.toString());
            extentTestThread.get().info("response body: " + response.getBody().asString());
            driver.navigate().refresh();
            Thread.sleep(6000);
            sa.assertEquals(driver.findElements(By.linkText("View or Change")).size(), "3");
            extentTestThread.get().log(Status.PASS, "Soft assertion passed: number of item in cart matched to "+driver.findElements(By.linkText("View or Change")).size());
            driver.findElement(By.id("toggleNavMenu")).click();
            Thread.sleep(6000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Admin']")))).click();
            driver.findElement(By.xpath("//span[text()='Proceed to Checkout']")).click();
            Thread.sleep(6000);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[span[text()='Checkout']]")))).click();
            Thread.sleep(10000);
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            WebElement downloadPDFButton = (WebElement) jse.executeScript(
                    "return document.querySelector('#app > section > section > div > div > main > div:nth-child(3) > div > div > div.container-fluid > div:nth-child(1) > div.m-b-xs._2_hnXaSd-5fgS8TMoKItZA > div.page-header > div > div > button:nth-child(1)')");
            Thread.sleep(6000);
            jse.executeScript("arguments[0].scrollIntoView(true)", downloadPDFButton);
            jse.executeScript("arguments[0].click()", downloadPDFButton);
            sa.assertAll();
        } catch (Exception e) {
            logger.severe(e.toString());
            Assert.fail();
        }
        finally {
            return driver;
        }

    }

    public WebDriver pdfCheck(WebDriver driver){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try {
            Thread.sleep(6000);
            driver.get("chrome://downloads/");
            sa.assertEquals((String) jse.executeScript("document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#file-link').textContent"), "confirmation.pdf");
            extentTestThread.get().log(Status.PASS, "Soft assertion passed: PDF found");
            sa.assertAll();
        } catch (Exception e) {
            logger.severe(e.toString());
            Assert.fail();
        }
        finally {
            return driver;
        }

    }
}
