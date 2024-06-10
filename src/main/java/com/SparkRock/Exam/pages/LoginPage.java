package com.SparkRock.Exam.pages;
import com.SparkRock.Exam.test.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    public WebElement usernameInputLocator;

    @FindBy(id = "password")
    public WebElement passwordInputLocator;

    @FindBy(id = "login-button")
    public WebElement loginButtonLocator;

    @FindBy(xpath = "//h3[@data-test='error']")
    public WebElement errorInvalidUsernameOrPasswordText;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private void setUsername(String username){
        enterText(usernameInputLocator,username);
    }

    private void setPassword(String password){
        enterText(passwordInputLocator,password);
    }

    private void clickSubmitButton(){
        click(loginButtonLocator);
    }

    public void loginUser(String username, String password){
        setUsername(username);
        setPassword(password);
        clickSubmitButton();
    }


    public String incorrectUsernameOrPasswordTextIsVisible() throws Exception {
        driverWait.until(ExpectedConditions.visibilityOf(errorInvalidUsernameOrPasswordText));
        return getElementText(errorInvalidUsernameOrPasswordText);
    }

}

