import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    public AppiumDriver driver;
    @Before
    public void setUp() throws Exception
    {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "6.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\Mobile_automation\\GitHub\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown()
    {
        driver.quit();
    }

    @Test
    public void myFirstTest()
    {
        waitElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        waitElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can't find seach input",
                5
        );

        waitElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching",
                15
        );
    }

    @Test
    public void testCancelSearch()
    {
        waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia input'",
                5
        );

        waitElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5
        );

        waitElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is still present on the page",
                5
        );
    }

    @Test
    public void testCompareArticleTitle()
    {
        waitElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        waitElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Can't find seach input",
                5
        );
        waitElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Can't find 'Object-oriented programming language' topic searching",
                10
        );

        WebElement title_element = waitElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                20
        );
        String acticle_title = title_element.getAttribute("text");
        Assert.assertEquals(
                "Get unexpected title",
                "Java (programming language)",
                acticle_title
        );

    }

    @Test
    public void test_ex2_check_element_text()
    {
        waitElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        boolean result = checkTextOfElement(
                By.id("org.wikipedia:id/search_src_text"),
                "Can't find seach input",
                "Search…"
        );

        Assert.assertTrue("Get unexpected text in search input", result);

    }

    @Test
    public void test_ex3_CancelSearchResults()
    {
        waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia input'",
                5
        );

        waitElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Can't find seach input",
                5
        );

        List<WebElement> search_results = waitElementsPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Can't find search results",
                15

        );

        int results_count = search_results.size();
        System.out.println("search result count = " + results_count);
        Assert.assertTrue("Get empty seach result list!",results_count > 1);

        waitElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5
        );

        boolean result = waitElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Search result is still present on the page",
                7
        );

    }

    @Test
    public void test_ex4_CheckSearchResults() {
        String searchTerm = "Java";

        waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia input'",
                5
        );

        waitElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchTerm,
                "Can't find seach input",
                5
        );

        List<WebElement> search_results = waitAllElementsPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Can't find search results",
                15
        );

        System.out.println("search result count = " + search_results.size());

        for(int i=0;i<search_results.size();i++){
            String text = search_results.get(i).getText();
            //System.out.println("Element text = " + text);

            Assert.assertTrue("Search result validation failed at element [+ i +].", text.contains(searchTerm));
        }
    }

    private WebElement waitElementPresent(By by, String error_message, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitElementPresent(By by, String error_message)
    {
        return waitElementPresent(by, error_message, 5);
    }

    private WebElement waitElementAndClick(By by, String error_msg, long timeoutSeconds)
    {
        WebElement element = waitElementPresent(by, error_msg, timeoutSeconds);
        element.click();
        return element;
    }
    private WebElement waitElementAndSendKeys(By by, String value, String error_msg, long timeoutSeconds)
    {
        WebElement element = waitElementPresent(by, error_msg, timeoutSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitElementNotPresent(By by, String error_msg, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private boolean checkTextOfElement(By by, String error_msg, String expect_text)
    {
        WebElement element = waitElementPresent(by, error_msg);
        return element.getAttribute("text").equals(expect_text);

    }

    private List<WebElement> waitElementsPresent(By by, String error_msg, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    private List<WebElement> waitAllElementsPresent(By by, String error_msg, long timeoutSeconds)
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(error_msg + "\n");
        return  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

}
