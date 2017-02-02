package pageobjects.LiveSite;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by kelvint on 11/14/16.
 */
public class RSSFeedsPage extends AbstractPageObject{

    private final By pressReleaseFeeds;
    private final By eventsFeeds;
    private final By presentationFeeds;
    private final By rssFeedIcon;
    private final By feedsExist; //amount of VISIBLE lines should be greater than 11

    private JavascriptExecutor executor = (JavascriptExecutor) driver;

    public RSSFeedsPage(WebDriver driver) {
        super(driver);
        pressReleaseFeeds = By.partialLinkText(propUIPublicSite.getProperty("pressReleaseFeeds"));
        eventsFeeds = By.partialLinkText(propUIPublicSite.getProperty("eventsFeeds"));
        presentationFeeds = By.partialLinkText(propUIPublicSite.getProperty("presentationFeeds"));
        rssFeedIcon = By.xpath(propUIPublicSite.getProperty("rssFeedIcon"));
        feedsExist = By.className(propUIPublicSite.getProperty("feedsExist")); //amount of VISIBLE lines should be greater than 11
    }

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

        for(int x = 0; x < driver.switchTo().window(tabs.get(tabs.size()-1)).findElements(feedsExist).size(); x++) {
            if (driver.findElements(feedsExist).get(x).isDisplayed()) {
                amount++;
            }
        }

        if (amount <= 11 || tabs.size() < 2) {
            return false;
        }

        executor.executeScript("window.close();");
        tabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));

        return true;
    }

    public boolean eventRSSExists()
    {
        findElement(eventsFeeds).click();
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        int amount = 0;

        for(int x = 0; x < driver.switchTo().window(tabs.get(tabs.size()-1)).findElements(feedsExist).size(); x++) {
            if (driver.findElements(feedsExist).get(x).isDisplayed()) {
                amount++;
            }
        }

        if (amount <= 11 || tabs.size() < 2) {
            return false;
        }

        executor.executeScript("window.close();");
        tabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));

        return true;
    }

    public boolean presentationRSSExists()
    {
        findElement(presentationFeeds).click();
        ArrayList<String> tabs = new ArrayList(driver.getWindowHandles());
        int amount = 0;

        for(int x = 0; x < driver.switchTo().window(tabs.get(tabs.size()-1)).findElements(feedsExist).size(); x++) {
            if (driver.findElements(feedsExist).get(x).isDisplayed()) {
                amount++;
            }
        }

        if (amount <= 11 || tabs.size() < 2) {
            return false;
        }

        executor.executeScript("window.close();");
        tabs = new ArrayList(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size()-1));

        return true;
    }
}
