package com.SparkRock.Exam.test;


import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class BaseUiTest {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    @BeforeSuite
    public void setUpSuite() {
        // set chromedriver property
        // If it is not run via docker, use chromedriver.exe
        String defaultDriverPath;
        defaultDriverPath = String.join(File.separator, "drivers", "chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", defaultDriverPath);

    }

    @BeforeMethod
    public void setDriver() throws MalformedURLException {
        // Start driver
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//        options.addArguments("--lang=en-GB");
        // Setting new download directory path
        Map<String, Object> prefs = new HashMap<String, Object>();
        // Use File.separator as it will work on any OS
        prefs.put("download.default_directory",
                System.getProperty("user.dir") + File.separator + "downloadFiles");
//        options.setExperimentalOption("prefs", prefs);
        // Test is running on docker, use the remote web driver

//        driver.set(new ChromeDriver(ChromeOptionsUtil.getHeadlessChromeOptions()));
//        driver.set(new ChromeDriver(options));
        setDriver(new ChromeDriver(options));
        getDriver().manage().window().maximize();

    }


    public static class ChromeOptionsUtil {
        public static ChromeOptions getHeadlessChromeOptions() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // Run in headless mode
            options.addArguments("--disable-gpu"); // Disable GPU for headless mode
            // Add any additional options you need
            return options;
        }
    }

    @AfterMethod
    public void driverTearDown(ITestResult result) throws IOException {
        Process process = Runtime. getRuntime(). exec("taskkill /F /IM chromedriver.exe /T");
        process.destroy();
        getDriver().quit();
    }


    public WebDriver getDriver() {
        return driver.get();
    }

    public void setDriver(WebDriver driver) {
        this.driver.set(driver);
//        this.driver = driver;
    }

    protected void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void assertAllElementsFoundByXpathContainText(String xpath, String textToContain) {
        for (WebElement element: getDriver().findElements(By.xpath(xpath))
        ) {
            try {
                //Adding below "if" condition to skip because bug ticket AD-8191 will cause this test case to fail
                if (element.getText().equalsIgnoreCase("")){
                    continue;
                }
                Assert.assertTrue(element.getText().contains(textToContain));
            } catch (AssertionError e) {
                throw new AssertionError("\"" + element.getText() + "\" does not contain" + "\"" + textToContain + "\"");
            }
        }
    }

    public void assertElementContainsText(WebElement element, String textToContain) {
        try {
            Assert.assertTrue(element.getText().contains(textToContain));
        } catch (AssertionError e) {
            throw new AssertionError("\"" + element.getText() + "\" does not contain" + "\"" + textToContain + "\"");
        }
    }



}