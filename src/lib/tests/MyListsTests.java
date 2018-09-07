package lib.tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListsPageObject;
import lib.ui.NavigateUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {

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
        ArticlePageObject.addArticleToMyList(name_of_folder, true);
        ArticlePageObject.closeArticle();

        NavigateUI NavigateUI = new NavigateUI(driver);
        NavigateUI.clickMyLists();

        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);
        MyListsPageObject.swipeArticleToDelete(article_title);
    }

    @Test
    public void test_ex5_SaveTwoArticleToListAndThenDeleteOne()
    {
        // add first article
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitTitleElement();
        String first_article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "My test articles";
        ArticlePageObject.addArticleToMyList(name_of_folder, true);
        ArticlePageObject.closeArticle();

        //add second article
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleSubstring("Appium");

        ArticlePageObject.waitTitleElement();
        String second_article_title = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addArticleToMyList(name_of_folder, false);
        ArticlePageObject.closeArticle();

        NavigateUI NavigateUI = new NavigateUI(driver);
        NavigateUI.clickMyLists();

        // check that in list 2 articles
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);
        MyListsPageObject.openFolderByName(name_of_folder);

        MyListsPageObject.checkArticlePresentInList(first_article_title);
        MyListsPageObject.checkArticlePresentInList(second_article_title);

        // delete first article from list
        MyListsPageObject.swipeArticleToDelete(first_article_title);
        MyListsPageObject.waitArticleToDisappearByTitle(first_article_title);
        MyListsPageObject.waitArticleToAppearByTitle(second_article_title);

        //check title of existing article
        MyListsPageObject.openArticleByTitle(second_article_title);
        String articleTittle = ArticlePageObject.getArticleTitle();
        assertEquals("We see unexpected tittle!", second_article_title, articleTittle);
    }
}
