package pageobjects.LiveSite;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.io.IOException;
import java.util.ArrayList;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by kelvint on 11/14/16.
 */
public class RSSFeedsPage extends AbstractPageObject{

    private final By pressReleaseFeeds;
    private final By eventsFeeds;
    private final By presentationFeeds;
    private final By SECFeeds = By.xpath("//a[contains(@class,'RssLinkTop')]");
    private final By rssFeedIcon;
    //private final By feedsExist; //amount of VISIBLE lines should be greater than 11
    //if <rss exists, and there is at least one item
    private final By feedsExist;
    private final By itemInFeeds = By.xpath("//item[1]");

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

    public boolean SECReleaseRSSExists(){

        boolean results = false;

        //checking if there is a link assigned to it
        String url = findElement(SECFeeds).getAttribute("href");
        if(url.contains("rss/SECFiling.aspx")){
            results =  true;
        }
        else {
            System.out.println(url);
            results = false;
            return results;
        }

        return results;

    }

    public boolean pressReleaseRSSExists() //new issue where the tests can work individually but together, they fail... even with the newly added driver.close
    {
        String url = findElement(pressReleaseFeeds).getAttribute("href");


        if(url.contains("rss/pressrelease.aspx")){
            return true;
        }
        else {
            System.out.println(url);
            return false;
        }
    }

    public boolean eventRSSExists()
    {
        String url = findElement(eventsFeeds).getAttribute("href");


        if(url.contains("rss/event.aspx")){
            return true;
        }
        else {
            System.out.println(url);
            return false;
        }
    }

    public boolean presentationRSSExists()
    {
        String url = findElement(presentationFeeds).getAttribute("href");


        if(url.contains("rss/presentation.aspx")){
            return true;
        }
        else {
            System.out.println(url);
            return false;
        }
    }
}
