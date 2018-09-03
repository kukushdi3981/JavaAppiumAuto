package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEACH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    // ========= TEMPLATES METHODS ========
    private static String getSearchResult(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    // ====================================

    public void initSearchInput()
    {
        this.waitElementAndClick(By.xpath(SEACH_INIT_ELEMENT), "Can't find and click search init element", 5);
        this.waitElementPresent(By.xpath(SEACH_INIT_ELEMENT), "Can't find search input after click on search init element");
    }

    public void typeSearchLine(String search_line)
    {
        this.waitElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Can't find and type into search lime", 5);
    }

    public void waitSearchResult(String substring)
    {
        String search_result_xpath = getSearchResult(substring);
        this.waitElementPresent(By.xpath(search_result_xpath), "Can't find search result with text " + substring);
    }

    public void waitCancelButtonToAppear()
    {
        this.waitElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Can't find search cancel button", 5);
    }

    public void waitCancelButtonToDisappear()
    {
        this.waitElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present", 5);
    }

    public void clickCancelSearchBtn()
    {
        this.waitElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Can't click cancel search button", 5);
    }

    public void clickByArticleSubstring(String substring)
    {
        String search_result_xpath = getSearchResult(substring);
        this.waitElementAndClick(By.xpath(search_result_xpath), "Can't click search result with text " + substring, 10);
    }

    public int getAmountOfFoundAticles()
    {
        this.waitElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT), "Cannot find anything by the request ", 15);

        return this.getAmountOfElements(By.xpath(SEARCH_RESULT_ELEMENT));

    }

    public void waitEmptyResultsLabel()
    {
        this.waitElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "Can't find empty result element!", 15);

     }

     public void assertThereNoResultOfSearch()
     {
         this.assertElementNotPresent(By.xpath(SEARCH_RESULT_ELEMENT), "We supposed not to find any results");
     }
}
