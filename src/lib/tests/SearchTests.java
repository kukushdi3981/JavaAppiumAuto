package lib.tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

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
}
