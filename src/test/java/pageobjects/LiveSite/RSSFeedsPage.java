package pageobjects.LiveSite;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;

/**
 * Created by kelvint on 11/14/16.
 */
public class RSSFeedsPage extends AbstractPageObject{

    private final By pressReleaseFeeds = By.partialLinkText("Press Release RSS Feed");
    private final By eventsFeeds = By.partialLinkText("Event RSS Feed");
    private final By presentationFeeds = By.partialLinkText("Presentation RSS Feed");
    private final By rssFeedIcon = By.xpath("//img[@src='//s2.q4cdn.com/175719177/files/design/rssicon.gif']");
    private final By feedsExist = By.className("line"); //amount of VISIBLE lines should be greater than 11

    public RSSFeedsPage(WebDriver driver) { super(driver); }

    public boolean rssFeedsExist ()
    {
        if(findElements(rssFeedIcon).size() != 3)
        {
            return false;
        }

        return findElement(rssFeedIcon).isDisplayed() && doesElementExist(pressReleaseFeeds) &&
                doesElementExist(eventsFeeds) &&
                doesElementExist(pressReleaseFeeds);   //check for icon and each feed. NOT COMPLETE
    }

    public boolean pressReleaseRSSExists() //new issue where the tests can work individually but together, they fail... even with the newly added driver.close
    {
        findElement(pressReleaseFeeds).click();
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        int amount = 0;

        for(int x = 0; x < driver.switchTo().window(tabs.get(1)).findElements(feedsExist).size(); x++) {
            if (driver.findElements(feedsExist).get(x).isDisplayed()) {
                amount++;
            }
        }

        if (amount <= 11 || tabs.size() != 2) {
            return false;
        }

        return true;
    }

    public boolean eventRSSExists()
    {
        findElement(eventsFeeds).click();
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        int amount = 0;

        for(int x = 0; x < driver.switchTo().window(tabs.get(1)).findElements(feedsExist).size(); x++) {
            if (driver.findElements(feedsExist).get(x).isDisplayed()) {
                amount++;
            }
        }

        if (amount <= 11 || tabs.size() != 2) {
            return false;
        }

        return true;
    }

    public boolean presentationRSSExists()
    {
        findElement(presentationFeeds).click();
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        int amount = 0;

        for(int x = 0; x < driver.switchTo().window(tabs.get(1)).findElements(feedsExist).size(); x++) {
            if (driver.findElements(feedsExist).get(x).isDisplayed()) {
                amount++;
            }
        }

        if (amount <= 11 || tabs.size() != 2) {
            return false;
        }

        return true;
    }
}
