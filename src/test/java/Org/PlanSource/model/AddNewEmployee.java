package Org.PlanSource.model;

import Org.PlanSource.Listeners.ExtentTestListener;
import Org.PlanSource.PageObject.AddEmployeePage;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public class AddNewEmployee extends ExtentTestListener {
    private static final Logger logger = Logger.getLogger(AddNewEmployee.class.getName());
    private static final SoftAssert sa =new SoftAssert();

    public static WebDriver addDetails(WebDriver driver) throws IOException {
        WebDriverWait explicitWait =new WebDriverWait(driver, Duration.ofSeconds(5));
        AddEmployeePage employee =new AddEmployeePage();
        Properties prop;
        FileInputStream fis=null;
        try{
            prop = new Properties();
            fis = new FileInputStream("src/test/resources/config.properties");
            prop.load(fis); // loading properties file
            employee.setAdd_employee_link(driver.findElement(By.linkText("Add a New Employee")));
            employee.getAdd_employee_link().click();
            employee.setPassword(driver.findElement(By.id("password")));
            explicitWait.until(ExpectedConditions.visibilityOf(employee.getPassword()));
            employee.getPassword().sendKeys("12345678qqewerrtrtt");
            employee.setFirstName(driver.findElement(By.id("first_name")));
            employee.getFirstName().sendKeys(prop.getProperty("firstname"));
            employee.setLastName(driver.findElement(By.id("last_name")));
            employee.getLastName().sendKeys(prop.getProperty("lastname"));
            employee.setSSN(driver.findElement(By.id("ssn_text")));
            Random random= new Random();
            // because we needed random SSN with length of 9
            employee.getSSN().sendKeys(Integer.toString(random.nextInt(999999999-100000000)+100000000));
            employee.setAddress(driver.findElement(By.id("address_1")));
            employee.getAddress().sendKeys("882, Dubey Colony");
            employee.setCity(driver.findElement(By.id("city")));
            employee.getCity().sendKeys("Katni");
            employee.setState(driver.findElement(By.id("stateTypeahead")));
            employee.getState().sendKeys("South Carolina");
            employee.setZip(driver.findElement(By.id("zip_code")));
            employee.getZip().sendKeys("29899");
            employee.setCountry(driver.findElement(By.id("countryTypeahead")));
            employee.getCountry().sendKeys("United States");
            employee.setBirthdate(driver.findElement(By.id("birthdate")));
            employee.getBirthdate().sendKeys("10/08/1996");
            employee.setGender(driver.findElement(By.id("gender")));
            Select dropdown= new Select(employee.getGender());
            dropdown.selectByValue("M");
            employee.setMaritalStatus(driver.findElement(By.id("marital_status")));
            dropdown=new Select(employee.getMaritalStatus());
            dropdown.selectByValue("S");
            //to get date of two ago
            LocalDate dateOnTwoDaysAgo= LocalDate.now().minusDays(2);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formattedDate = dateOnTwoDaysAgo.format(formatter);

            employee.setHireDate(driver.findElement(By.id("hire_date")));
            employee.getHireDate().sendKeys(formattedDate);
            employee.setEligibleStartDate(driver.findElement(By.id("benefits_start_date")));
            employee.getEligibleStartDate().sendKeys(formattedDate);
            employee.setEmploymentLevel(driver.findElement(By.id("employment_level")));
            dropdown=new Select(employee.getEmploymentLevel());
            dropdown.selectByValue("F");
            employee.setLocation(driver.findElement(By.id("location")));
            dropdown=new Select(employee.getLocation());
            dropdown.selectByValue("SCA");
            employee.setCurrent_salary(driver.findElement(By.id("current_salary")));
            employee.getCurrent_salary().sendKeys("1200");
            employee.setBenefit_salary(driver.findElement(By.id("benefit_salary")));
            employee.getBenefit_salary().sendKeys("1000");

            driver.findElement(By.id("btn_save")).click();
            explicitWait.until(ExpectedConditions.titleIs("Employee Profile"));
            sa.assertEquals("Employee Profile", driver.getTitle());
            sa.assertAll();
            extentTestThread.get().log(Status.PASS, "Page Title 'Employee Profile' matched");

        }
        catch (Exception e){
            logger.severe(e.toString());
            Assert.fail();
        }
        finally {
            Assert.assertNotNull(fis);
            fis.close();

        }
        return driver;
    }
}
