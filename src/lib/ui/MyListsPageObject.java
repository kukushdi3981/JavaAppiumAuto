package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject{

    private static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_TITLE_TPL = "//*[@text='{TITLE}']";

    private static String getFolderXPathByName(String folder_name)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", folder_name);
    }

    private static String getSavedArticleXPath(String article_title)
    {
        return ARTICLE_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        String folder_name_xpath = getFolderXPathByName(name_of_folder);
        this.waitElementAndClick(
                By.xpath(folder_name_xpath),"Cannot find folder by name" + name_of_folder,5);

    }

    public void waitArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXPath(article_title);
        this.waitElementNotPresent(By.xpath(article_xpath), "Saved article still present with title" + article_title, 15);
    }

    public void waitArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXPath(article_title);
        this.waitElementPresent(By.xpath(article_xpath), "Can't find saved article with title" + article_title, 15);
    }

    public void swipeArticleToDelete(String article_title)
    {
        this.waitArticleToAppearByTitle(article_title);

        String article_xpath = getSavedArticleXPath(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),"Cannot find saved article in My reading list");

        this.waitArticleToDisappearByTitle(article_title);
    }

    public void checkArticlePresentInList(String article_name)
    {
        String article_element_xpath = getSavedArticleXPath(article_name);
        this.waitElementPresent(
                By.xpath(article_element_xpath),
                "Cannot find '" + article_name + "' article in My reading list");
    }

    public void openArticleByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXPath(article_title);
        this.waitElementAndClick(By.xpath(article_xpath), "Cannot find '" + article_title + "' article in My reading list", 5);
    }

}
