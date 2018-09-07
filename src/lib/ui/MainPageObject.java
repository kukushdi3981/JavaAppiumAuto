package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver)
    {
        this.driver = driver;
    }

    public WebElement waitElementPresent(By by, String error_message, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitElementPresent(By by, String error_message)
    {
        return waitElementPresent(by, error_message, 5);
    }

    public WebElement waitElementAndClick(By by, String error_msg, long timeoutSeconds)
    {
        WebElement element = waitElementPresent(by, error_msg, timeoutSeconds);
        element.click();
        return element;
    }

    public WebElement waitElementAndSendKeys(By by, String value, String error_msg, long timeoutSeconds)
    {
        WebElement element = waitElementPresent(by, error_msg, timeoutSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitElementNotPresent(By by, String error_msg, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitElementAndClear(By by, String error_msg, long timeoutSeconds)
    {
        WebElement element = waitElementPresent(by, error_msg, timeoutSeconds);
        element.clear();
        return element;
    }

    public boolean checkTextOfElement(By by, String error_msg, String expect_text)
    {
        WebElement element = waitElementPresent(by, error_msg);
        return element.getAttribute("text").equals(expect_text);

    }

    public List<WebElement> waitElementsPresent(By by, String error_msg, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public List<WebElement> waitAllElementsPresent(By by, String error_msg, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    public void  swipeUp(int timeOfSwipe)
    {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    public void swipeElementToLeft(By by, String error_message)
    {
        WebElement element = waitElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(500)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    public void swipeUpQuick()
    {
        swipeUp(200);
    }

    public void  swipeUpToFindElement(By by, String error_message, int maxSwipes)
    {
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0){
            if (alreadySwiped > maxSwipes){
                waitElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public int getAmountOfElements(By by)
    {
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String errorMessage)
    {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public  String waitElementAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds)
    {
        WebElement element = waitElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void assertElementPresent(By by, String errorMessage)
    {
        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements == 0) {
            String defaultMessage = "An element '" + by.toString() + "' supposed to be present!";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
