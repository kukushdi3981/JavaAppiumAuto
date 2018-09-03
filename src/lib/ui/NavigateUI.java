package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigateUI extends MainPageObject{

    private static final String
        MY_LIST_LINK = "//android.widget.FrameLayout[@content-desc='My lists']";

    public NavigateUI(AppiumDriver driver)
    {
        super(driver);
    }

    public void clickMyLists()
    {
        this.waitElementAndClick(
                By.xpath(MY_LIST_LINK),"Cannot find 'My lists' navigation button",5);

    }
}
