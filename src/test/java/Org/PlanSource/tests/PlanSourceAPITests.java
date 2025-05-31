package Org.PlanSource.tests;

import BaseClass.BaseClass;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class PlanSourceAPITests extends BaseClass {
    private final SoftAssert sa =new SoftAssert();

    @Test(priority = 4, dependsOnMethods = {"Org.PlanSource.tests.PlanSourceUITests.AddMedical_and_VoluntaryBenefits"})
    public void addDentalBenefit(){
        extentTest =extentReports.createTest(new Exception().getStackTrace()[0].getMethodName(),"Add Dental benefits through API call");
        extentTestThread.set(extentTest);
        Cookie driverCookie = driver.manage().getCookieNamed("_session_id");
        Assert.assertNotNull(driverCookie);
        var URI="https://partner-dev-benefits.plansource.com/v1/self_service/coverages";
        var payload="""
                {
                "election":
                    {
                     "coverage_level_id": 137,
                     "dependent_ids": [],
                     "org_plan_id": 319901188
                    },
                "enrollment_context_type": "initial",
                "include_benefits_in_response": True,
                "include_related_coverage_changes": True,
                "life_event_completed": False,
                "org_benefit_id":121137668
                }
                """;
        Map<String, String> cookieDict = new HashMap<>();
        cookieDict.put("_session_id", driverCookie.getValue().replace("_session_id=", ""));
        Map<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://partner-dev-benefits.plansource.com/subscriber/benefit/121137668");
        Response response= given().cookies(cookieDict).headers(headers).body(payload).contentType("application/json")
                .when().put(URI);
        extentTest.info("Status code of API call is "+response.getStatusCode());
        //assert on status code
        response.then().log().body().assertThat().statusCode(200);
        extentTest.info("response body: "+response.getBody());

        driver.navigate().refresh();

        sa.assertEquals(driver.findElements(By.linkText("View or Change")).size(),"4");
        driver.findElement(By.id("toggleNavMenu")).click();
        driver.findElement(By.xpath("//span[text()='Admin']")).click();
        driver.findElement(By.xpath("//span[text()='Proceed to Checkout']")).click();
        driver.findElement(By.xpath("//button[span[text()='Checkout']]")).click();
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("document.querySelector('#app > section > section > div > div > main > div:nth-child(3) > div > div > div.container-fluid > div:nth-child(1) > div.m-b-xs._2_hnXaSd-5fgS8TMoKItZA > div.page-header > div > div > button:nth-child(1)').click()");

    }
    @Test(priority = 5, dependsOnMethods = {"addDentalBenefit"})
    public void checkPdfConfirmation(){
        driver.get("chrome://downloads/");
        sa.assertEquals(driver.findElement(By.linkText("confirmation.pdf")).getText(),"confirmation.pdf");

    }


}
