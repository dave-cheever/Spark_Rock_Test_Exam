package com.SparkRock.Exam.pages;

import com.SparkRock.Exam.test.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//div[contains(text(),'Swag Labs')]")
    public WebElement homepageHeaderLabel;

    public boolean isHeaderLabelVisible(){
        return homepageHeaderLabel.isDisplayed();
    }
}
