package lib.tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        //SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitCancelButtonToAppear();
        SearchPageObject.clickCancelSearchBtn();
        SearchPageObject.waitCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Linkin park discography";
        SearchPageObject.typeSearchLine(searchLine);

        int amounOfSearchResults = SearchPageObject.getAmountOfFoundAticles();

        assertTrue(
                "Found to few results",
                amounOfSearchResults > 0
        );
    }

    @Test
    public void testNoResultsFounds()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "zxcvasdfqwer";
        SearchPageObject.typeSearchLine(searchLine);
        SearchPageObject.waitEmptyResultsLabel();
        SearchPageObject.assertThereNoResultOfSearch();
    }

    @Test
    public void test_ex2_check_element_text()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        boolean result = SearchPageObject.checkElementText();
        assertTrue("Get unexpected text in search input", result);

    }

    @Test
    public void test_ex3_CancelSearchResults()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");

        int results_count = SearchPageObject.waitSearchResultsAndCountResultList();
        assertTrue("Get empty search result list!",results_count > 1);

        SearchPageObject.clickCancelSearchBtn();
        //это действие нужно, чтобы отрабатывала последующая проверка. Без нее тест всегда падает
        SearchPageObject.clickCancelSearchBtn();
        SearchPageObject.waitSearchResultListToDisappear();
    }

    @Test
    public void test_ex4_CheckSearchResults() {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        String searchTerm = "Java";

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchTerm);

        List<WebElement> search_results = SearchPageObject.waitAppearAllSearchResults();
        System.out.println("search result count = " + search_results.size());

        for(int i=0;i<search_results.size();i++){
            String text = search_results.get(i).getText();
            //System.out.println("Element text = " + text);

            assertTrue("Search result validation failed at element [+ i +].", text.contains(searchTerm));
        }
    }
}
