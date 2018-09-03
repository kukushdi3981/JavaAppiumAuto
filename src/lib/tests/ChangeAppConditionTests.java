package lib.tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

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

        assertEquals(
                "Article tittle have been changed after screen rotation",
                tittleBeforeRotation,
                tittleAfterRotation
        );
        //поворачиваем второй раз
        this.rotateScreenPortrait();
        String tittleAfterSecondRotation = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Article tittle have been changed after second screen rotation",
                tittleBeforeRotation,
                tittleAfterSecondRotation
        );
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
    }
}
