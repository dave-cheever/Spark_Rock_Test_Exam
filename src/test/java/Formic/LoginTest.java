package Formic;

import com.SparkRock.Exam.pages.HomePage;
import com.SparkRock.Exam.pages.LoginPage;
import com.SparkRock.Exam.test.BasePage;
import com.SparkRock.Exam.test.BaseUiTest;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class LoginTest extends BaseUiTest{


    LoginPage loginPage;
    HomePage homePage;


    @Test
    public void Verify_that_the_page_displays_error_message_for_invalid_username_or_password() throws Exception{
        BasePage.visit();
        loginPage.loginUser("Test123","password");
        Assert.assertTrue(loginPage.incorrectUsernameOrPasswordTextIsVisible()
                .equals("Epic sadface: Username and password do not match any user in this service"));
    }

    @Test
    public void Verify_that_the_page_displays_error_message_for_username_required() throws Exception {
        BasePage.visit();
        loginPage.loginUser("","secret_sauce");
        Assert.assertTrue(loginPage.incorrectUsernameOrPasswordTextIsVisible()
                .equals("Epic sadface: Username is required"));
    }

    @Test
    public void Verify_that_the_page_displays_error_message_for_password_required() throws Exception {
        BasePage.visit();
        loginPage.loginUser("standard_user","");
        Assert.assertTrue(loginPage.incorrectUsernameOrPasswordTextIsVisible()
                .equals("Epic sadface: Password is required"));
    }

    @Test
    public void Verify_that_the_user_successfully_login_given_the_correct_credentials() throws Exception {
        BasePage.visit();
        loginPage.loginUser("standard_user","secret_sauce");
        Assert.assertTrue(homePage.isHeaderLabelVisible());
    }



    @BeforeMethod
    public void initialisePageElements(ITestContext iTestContext) {
        loginPage = PageFactory.initElements(getDriver(), LoginPage.class);
        homePage = PageFactory.initElements(getDriver(), HomePage.class);
    }
}


