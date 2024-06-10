package com.SparkRock.Exam.test;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;



public class BasePage {

    protected static WebDriver driver;
    protected static WebDriverWait driverWait;


    static String nextPageButton = "//li//button//div[contains(text(),'Next Page')]";
    static String previousPageButton = "//li//button//div[contains(text(),'Previous Page')]";
    static String page = "//div[@data-testid='page-progress']";


    public BasePage(WebDriver driver){
        this.driver = driver;
        driverWait = new WebDriverWait(driver,Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public static void visit() {
        driver.get("https://www.saucedemo.com/");
    }

    public void javascriptClearInputValue(WebElement element){
        executeJavascript(element,"argument[0].value='';");
    }

    public void executeJavascript(WebElement element,String script){
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript(script,element);
    }

    public void javascriptClick(WebElement element) {
        executeJavascript(element,"arguments[0].click();");
    }

    public static void clickWithTries(WebElement element,int maxRetries) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                driverWait.until(ExpectedConditions.elementToBeClickable(element));
                scrollElementIntoView(driver,element);
                element.click();
                break;
            } catch (Exception e) {
                // Print exception details (you might want to log this)
                System.out.println("Attempt " + (i + 1) + " failed: " + e.getMessage());
                // If this is the last attempt, throw the exception to fail the test
                if (i == maxRetries - 1) {
                    Reporter.log("Element not visible. ");
                    throw e;
                }
            }
        }
    }

    public static void click(WebElement element) {
            try {
                driverWait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
            } catch (Exception e) {

                Reporter.log("Element not visible. ");
            }
    }

    public static void enterText(WebElement element, String textToEnter){
        driverWait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(textToEnter);
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    public static Integer getCurrentPage(){
        //code to get the current page
        try {
            driver.findElement(By.xpath(page));
            WebElement element = stringToWebElement(page);
            String currentPage = element.getText();
            String [] str = currentPage.split(" ");
            return Integer.parseInt(str[1]);
        }catch (NoSuchElementException e){
            return null;
        }
    }

    public static void waitForPageToLoad(){
        String test = "//a[@id='maincontent']//following-sibling::div/div/div";
        boolean flag = false;
        while(flag!=true){
            int num = driver.findElements(By.xpath(test)).size();
            if(num>1){
                break;
            }
        }
        sleep(2000);
    }



    public boolean isElementClickable(WebElement element){
        boolean result = false;
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(element));
            result = true;
        }catch (TimeoutException e){
//            recordScreenshot();
            Reporter.log("Element wasn't clickable");
            result =false;
        }
        return result;
    }

    public static void clickNextPage() {
        WebElement element = stringToWebElement(nextPageButton);
        click(element);
    }
    public static void clickPreviousPage() {
        WebElement element = stringToWebElement(previousPageButton);
        click(element);
    }



    public void enterTextWithDelay(WebElement element, String textToEnter, int milliseconds){
        driverWait.until(ExpectedConditions.visibilityOf(element));
        for(char c : textToEnter.toCharArray()){
            element.sendKeys(Character.toString(c));
            sleep(milliseconds);
        }
    }

    public String getElementText(WebElement element) {
        driverWait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    public void selectFromDropdown(String selectXpath, String selectVisibleText) {
        By byProductType = By.xpath(selectXpath);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(byProductType));
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byProductType));
        Select select = new Select(driver.findElement(byProductType));
        select.selectByVisibleText(selectVisibleText);
    }

    public void selectFromDropdown(String selectXpath, int selectIndex) {
        By byProductType = By.xpath(selectXpath);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(byProductType));
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byProductType));
        Select select = new Select(driver.findElement(byProductType));
        select.selectByIndex(selectIndex);
    }

    public void selectFromDropdown(WebElement element, String selectVisibleText) {
        By byProductType = By.xpath(getXpathOfWebElement(element));
        driverWait.until(ExpectedConditions.presenceOfElementLocated(byProductType));
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byProductType));
        Select select = new Select(driver.findElement(byProductType));
        select.selectByVisibleText(selectVisibleText);
    }

    public void selectFromDropdown(WebElement element, int selectIndex) {
        By byProductType = By.xpath(getXpathOfWebElement(element));
        driverWait.until(ExpectedConditions.presenceOfElementLocated(byProductType));
        driverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(byProductType));
        Select select = new Select(driver.findElement(byProductType));
        select.selectByIndex(selectIndex);
    }

    public void selectTextSearchResult() {
        // xpath of first search result
        String xpath = "//ul[@role='listbox']/li[1]";

        WebElement element = driver.findElement(By.xpath(xpath));
        scrollElementIntoView(driver, element);
        element.click();
    }

    public void waitForTextNotification(String text) {
        String xpath = "//*[contains(text(), '$TEXT')]".replace("$TEXT", text);
        By byProductType = By.xpath(xpath);
        System.out.println("waiting for Element to be Found");
        driverWait.until(ExpectedConditions.presenceOfElementLocated(byProductType));
        System.out.println("Element Found");
    }

    public static String stringReplace(String element, String stringToReplace){
        element = element.replace("$TEXT",stringToReplace);
        return element;
    }

    public static String stringReplaceTwoValues(String elementOne, String stringToReplaceOne, String stringToReplaceTwo){
        elementOne = elementOne.replace("$TEXT",stringToReplaceOne);
        elementOne = elementOne.replace("$NUM",stringToReplaceTwo);
        return elementOne;
    }

    public static WebElement convertToWebElement(String element){
        By elem = By.xpath(element);
//        scrollElementIntoView(driver,driver.findElement(elem));
        WebElement webElem = driverWait.until(ExpectedConditions.visibilityOfElementLocated(elem));
        return webElem;
    }






    public static WebElement stringReplaceAndConvertToWebElement(String elem, String stringToReplace){
        elem = stringReplace(elem,stringToReplace);
        WebElement element = convertToWebElement(elem);
        return element;
    }

    public static boolean elementClickable(WebElement element){
        try {
            if(element.isEnabled()){
                return true;
            }
            return false;
        }catch (TimeoutException e){
//            recordScreenshot();
            Reporter.log("Element wasn't clickable");
            return false;
        }
    }

    public static void elementVisible(WebElement element){
        try {
            driverWait.until(ExpectedConditions.visibilityOf(element));
        }catch (TimeoutException e){
//            recordScreenshot();
            Reporter.log("Element wasn't visible.");
        }
    }

    public static int numberOfElementsVisible(String element){
        WebElement elem = stringToWebElement(element);
        scrollElementIntoView(driver,elem);
        elementVisible(elem);
        return driver.findElements(By.xpath(element)).size();
    }

    public void pressArrowDown(WebElement element){
        element.sendKeys(Keys.DOWN);
    }

    public void pressEnter(WebElement element){
        element.sendKeys(Keys.ENTER);
    }





    public void waitForTextNotificationToBeInvisible(String text) {
        String xpath = "//*[contains(text(), '$TEXT')]".replace("$TEXT", text);
        By byProductType = By.xpath(xpath);
        System.out.println("Waiting for element to be removed");
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(byProductType));
        System.out.println("element removed");
    }

    protected static void sleep(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    // Below method will be used to scroll element into view via
    // JavaScriptExecuter as some elements are not intractable until
    // they are scrolled into view
    protected static void scrollElementIntoView(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    protected static void scrollElementIntoView(WebDriver driver, By locator) {
        try {
            WebElement element = driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        } catch (Exception e) {
            System.out.println("Error scrolling to element: " + e.getMessage());
        }
    }

    // Check for element's visibility
    protected boolean isElementVisible(WebDriver driver, WebElement element) {
        Wait<WebDriver> wait = new FluentWait<>(driver);

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Check for element's visibility
    protected static boolean isElementVisible(WebDriver driver, String xpath) {
        Wait<WebDriver> wait = new FluentWait<>(driver);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementPresent(WebElement element) {
        try{
            elementVisible(element);
            return driver.findElements(By.xpath(getXpathOfWebElement(element))).size() > 0;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean isElementPresentBy(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getXpathOfWebElement(WebElement element) {
        return element.toString().substring(element.toString().indexOf("xpath: ") + 7, element.toString().length() - 1);
    }

    public void driverSwitchLastTab() {
        int numberOfTabs = driver.getWindowHandles().size();
        driver.switchTo().window(driver.getWindowHandles().toArray()[numberOfTabs-1].toString());
    }

    public void waitForElementText(WebElement elem, int seconds){
        int ctr = 0;
        for(boolean x = false; x == false;)
        {
            if(ctr == seconds*10){
                x = true;
            }
            else if (StringUtils.isBlank(elem.getText())){
                sleep(100);
                ctr++;
            }
            else{
                x = true;
            }
        }
    }

    public void waitForElementAttributeText(WebElement elem, int seconds){
        int ctr = 0;
        for(boolean x = false; x == false;)
        {
            if(ctr == seconds*10){
                x = true;
            }
            else if (StringUtils.isBlank(elem.getAttribute("value"))){
                sleep(100);
                ctr++;
            }
            else{
                x = true;
            }
        }
    }

    private String getNameOfFileDownloaded(WebDriver driver, String fileName, int millisecondsToStopWaiting) throws InterruptedException {
        driver.get("chrome://downloads");
        JavascriptExecutor je = (JavascriptExecutor) driver;
        long currentTime = System.currentTimeMillis();
        String result = null;

        while(true) {
            long b = System.currentTimeMillis();
            try {
                result = (String) je.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content  #file-link').text");
            } catch (JavascriptException e){
                continue;
            }

            if(b-currentTime == millisecondsToStopWaiting || result != null){
                break;
            }
            Thread.sleep(500);   //sleep half a seconds
        }
        return result;
    }

    public void switchToParentTab(){
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
        driver.close();
        driver.switchTo().window(tabs2.get(0));
    }

    public void switchToChildTab(){
        ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));
    }

    public WebElement getWebElement(String baseXpath, String stringToReplace, String stringToReplaceBy) {
        WebElement element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(baseXpath.replace(stringToReplace,stringToReplaceBy))));
        elementVisible(element);
        return element;
    }

    protected void waitUntilElementIsNotPresent(WebElement element, int millisecondsToStopWaiting) throws TimeoutException, InterruptedException {
        long currentTime = System.currentTimeMillis();
        while(true) {
            long b = System.currentTimeMillis();
            if(b-currentTime == millisecondsToStopWaiting){
                throw new TimeoutException("Element is still present");
            }

            Thread.sleep(500);   //sleep half a seconds
            if (!isElementPresent(element)) break;
        }
    }

    protected void waitUntilElementIsPresent(WebElement element, int millisecondsToStopWaiting) throws TimeoutException, InterruptedException {
        long waitTimeInSeconds = Math.round(millisecondsToStopWaiting / 1000.0);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeInSeconds));
        wait.until(ExpectedConditions.visibilityOf(element));

//        long b = 0;
//        while(true) {
//            if(b == millisecondsToStopWaiting){
//                throw new TimeoutException("Element is still present");
//            }
//
//            Thread.sleep(500);   //sleep half a seconds
//            if (isElementVisible(driver,element)){
//                break;
//            }
//            else{
//                b+=500;
//            }
//        }
    }



    public String removeZeroAtTheBeginning(String num){
        String strNum = Long.toString(Long.parseLong(num)); // convert integer to string
        if (strNum.charAt(0) == '0') {
            strNum = strNum.substring(1); // remove leading zero
        }
        return  strNum;
    }

    public static WebElement stringToWebElement(String element){
        WebElement elem;
        try{
            driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
            elem = driver.findElement(By.xpath(element));
            elementVisible(elem);
        }catch (TimeoutException e){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom='80%'");
            scrollElementIntoView(driver,driver.findElement(By.xpath(element)));
            elem = driver.findElement(By.xpath(element));
        }
        return elem;
    }



    public String emailAddressInputs(){
        Random rand = new Random();
        String[] emailProviders = {"gmail.com", "yahoo.com", "hotmail.com", "aol.com", "outlook.com"};
        String[] names = {"john", "jane", "doe", "smith", "miller"};
        int randomNameIndex = rand.nextInt(names.length);
        int randomProviderIndex = rand.nextInt(emailProviders.length);
        return names[randomNameIndex] + "@" + emailProviders[randomProviderIndex];
    }


    public static int generateRandomNumberNotEqualTo(int givenNumber, String formatMask) {
        Random random = new Random();

        if (formatMask == null) {
            // If formatMask is null, generate an integer in the full int range
            int generatedNumber;
            do {
                generatedNumber = random.nextInt();
            } while (generatedNumber == givenNumber);
            return generatedNumber;
        } else {
            // Determine the range based on the formatMask
            int minRange = 1;
            int maxRange = 0;

            for (int i = 0; i < formatMask.length(); i++) {
                if (formatMask.charAt(i) == '#') {
                    maxRange = maxRange * 10 + 9;
                } else {
                    throw new IllegalArgumentException("Invalid format mask. Use only '#' for digits.");
                }
            }

            if (maxRange == 0) {
                throw new IllegalArgumentException("Invalid format mask. Must contain at least one '#'.");
            }

            // Generate a random number within the specified range
            int generatedNumber;
            do {
                generatedNumber = minRange + random.nextInt(maxRange - minRange + 1);
            } while (generatedNumber == givenNumber);

            return generatedNumber;
        }
    }
}
