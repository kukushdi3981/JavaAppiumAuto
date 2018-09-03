import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

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
    public void testCompareArticleTitle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitTitleElement();
        String acticle_title = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Get unexpected title",
                "Java (programming language)",
                acticle_title
        );
    }

    @Test
    public void test_ex2_check_element_text()
    {
        MainPageObject.waitElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Can't find Search Wikipedia input",
                5
        );

        boolean result = MainPageObject.checkTextOfElement(
                By.id("org.wikipedia:id/search_src_text"),
                "Can't find seach input",
                "Search…"
        );

        Assert.assertTrue("Get unexpected text in search input", result);

    }

    @Test
    public void test_ex3_CancelSearchResults()
    {
        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia input'",
                5
        );

        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Can't find seach input",
                5
        );

        List<WebElement> search_results = MainPageObject.waitElementsPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Can't find search results",
                15

        );

        int results_count = search_results.size();
        System.out.println("search result count = " + results_count);

        Assert.assertTrue("Get empty seach result list!",results_count > 1);

        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find X to cancel search",
                5
        );

        boolean result = MainPageObject.waitElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                "Search result is still present on the page",
                7
        );

    }

    @Test
    public void test_ex4_CheckSearchResults() {
        String searchTerm = "Java";

        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia input'",
                5
        );

        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchTerm,
                "Can't find seach input",
                5
        );

        List<WebElement> search_results = MainPageObject.waitAllElementsPresent(
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

    @Test
    public void testSwipeArticle()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitTitleElement();
        ArticlePageObject.swipeToFooter();

    }

    @Test
    public void testSaveFirstArticleToMyList()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigateUI NavigateUI = new NavigateUI(driver);
        NavigateUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String searchLine = "Linkin park discography";
        SearchPageObject.typeSearchLine(searchLine);

        int amounOfSearchResults = SearchPageObject.getAmountOfFoundAticles();

        Assert.assertTrue(
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
    public void testChangeScreenOrientationOnSearchResults()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String tittleBeforeRotation = ArticlePageObject.getArticleTitle();
        //поворачиваем первый раз
        this.rotateScreenLandscape();
        String tittleAfterRotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article tittle have been changed after screen rotation",
                tittleBeforeRotation,
                tittleAfterRotation
        );
        //поворачиваем второй раз
        this.rotateScreenPortrait();
        String tittleAfterSecondRotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article tittle have been changed after second screen rotation",
                tittleBeforeRotation,
                tittleAfterSecondRotation
        );

        /*
        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String searchLine = "Java";

        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                searchLine,
                "Cannot find search input",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' searching by " + searchLine,
                15
        );

        String tittleBeforeRotation = MainPageObject.waitElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                20
        );

        //System.out.println("Title before rotation is: " + tittleBeforeRotation);

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String tittleAfterRotation = MainPageObject.waitElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                15
        );

        Assert.assertEquals(
                "Article tittle have been changed after screen rotation",
                tittleBeforeRotation,
                tittleAfterRotation
        );

        driver.rotate(ScreenOrientation.PORTRAIT);

        String tittleAfterSecondRotation = MainPageObject.waitElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find tittle of article",
                15
        );

        Assert.assertEquals(
                "Article tittle have been changed after second screen rotation",
                tittleBeforeRotation,
                tittleAfterSecondRotation
        );*/
    }

    @Test
    public void testCheckSearchArticleInBackground()
    {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitSearchResult("Object-oriented programming language");

        /*MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        driver.runAppInBackground(2);

        MainPageObject.waitElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' article after returning from background",
                5
        );*/
    }

    @Test
    public void test_ex5_SaveTwoArticleToListAndThenDeleteOne()
    {
        // add first article
        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );

        MainPageObject.waitElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' button",
                5
        );

        MainPageObject.waitElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find 'Name of the list' field",
                5
        );

        String nameOfFolder = "My test articles";

        MainPageObject.waitElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                nameOfFolder,
                "Cannot put text into 'Name of the list' field",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot find 'OK' button",
                5
        );
        MainPageObject.waitElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        //add second article
        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String second_acticle_name = "Appium";
        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                second_acticle_name,
                "Cannot find search input",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='" + second_acticle_name + "']"),
                "Cannot find '" + second_acticle_name + "' article",
                5
        );

        MainPageObject.waitElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find button to 'Add to reading list'",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find " + nameOfFolder + " list",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find 'X' button",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find 'My lists' button",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@text='" + nameOfFolder + "']"),
                "Cannot find " + nameOfFolder + " list",
                5
        );

        // check that in list 2 articles
        MainPageObject.waitElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' article in My reading list"
        );

        MainPageObject.waitElementPresent(
                By.xpath("//*[@text='"+ second_acticle_name + "']"),
                "Cannot find '" + second_acticle_name + "' article in My reading list"
        );

        // delete first article from list
        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find 'Java (programming language)' article in My reading list"
        );

        MainPageObject.waitElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5
        );

        MainPageObject.waitElementPresent(
                By.xpath("//*[@text='" + second_acticle_name + "']"),
                "Cannot find '" + second_acticle_name + "' article in My reading list"
        );

        //check title of existing article
        MainPageObject.waitElementAndClick(
                By.xpath("//*[@text='" + second_acticle_name + "']"),
                "Cannot find '" + second_acticle_name + "' article in My reading list",
                5
        );

        String articleTittle = MainPageObject.waitElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text']"),
                "text",
                "Cannot find article tittle",
                10
        );

        Assert.assertEquals(
                "We see unexpected tittle!",
                second_acticle_name,
                articleTittle
        );
    }

    @Test
    public void test_ex6_AssertArticleTitle()
    {
        MainPageObject.waitElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );

        MainPageObject.waitElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language",
                5
        );
        /*
        MainPageObject.waitElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article tittle",
                15
        );*/

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Article title not found!"
        );
    }

}
