package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTION_ADD_TO_MY_LIST_BTN = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        CLOSE_ARTICLE_BTN = "//android.widget.ImageButton[@content-desc='Navigate up']",
        NAME_ELEMENT_TPL = "//*[@text='{NAME}']";

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    // ========= TEMPLATES METHODS ========
    private static String getMyListElement(String substring)
    {
        return NAME_ELEMENT_TPL.replace("{NAME}", substring);
    }
    // ====================================


    public WebElement waitTitleElement()
    {
        return this.waitElementPresent(By.id(TITLE), "Can't find article title on page!", 20);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitTitleElement();
        return title_element.getAttribute("text");
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "Can't find the end of arcticle", 20);
    }

    public void addArticleToMyList(String name_of_folder, boolean isNewList)
    {
        this.waitElementAndClick(
                By.xpath(OPTIONS_BUTTON),"Cannot find 'More options' button", 5);

        this.waitElementAndClick(
                By.xpath(OPTION_ADD_TO_MY_LIST_BTN),"Cannot find button to 'Add to reading list'", 5);

        if(isNewList) {
            this.waitElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY), "Cannot find 'GOT IT' button", 5);

            this.waitElementAndClear(
                By.id(MY_LIST_NAME_INPUT), "Cannot find 'Name of the list' field", 5);

            this.waitElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT), name_of_folder, "Cannot put text into 'Name of the list' field", 5);

            this.waitElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON), "Cannot press 'OK' button", 5);
        }
        else{

            String my_list_element_xpath = getMyListElement(name_of_folder);
            this.waitElementAndClick(By.xpath(my_list_element_xpath),
                    "Cannot find '" + name_of_folder + "' list", 5);
        }
    }

    public void closeArticle()
    {
        this.waitElementAndClick(
                By.xpath(CLOSE_ARTICLE_BTN),"Cannot find 'X' button",5);

    }

    public void checkAppearTitleElement()
    {
        this.assertElementPresent(
                By.id(TITLE), "Article title not found!");

    }

}
