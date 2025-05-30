package Org.PlanSource.PageObject;

import org.openqa.selenium.WebElement;

public class PlanSourceLoginPage {
    public WebElement username;
    public WebElement password;
    public WebElement submit;

    public WebElement getUsername() {
        return username;
    }

    public void setUsername(WebElement username) {
        this.username = username;
    }

    public WebElement getPassword() {
        return password;
    }

    public void setPassword(WebElement password) {
        this.password = password;
    }

    public WebElement getSubmit() {
        return submit;
    }

    public void setSubmit(WebElement submit) {
        this.submit = submit;
    }
}
