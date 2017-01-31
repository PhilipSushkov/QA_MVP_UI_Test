package specs.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.LiveEvents;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

/**
 * Created by easong on 1/24/17.
 */
public class EventsPreviewPage extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private CheckPublicSite publicTests = new CheckPublicSite();
    private static HomePage homePage;
    private static LiveEvents liveEvents;

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
        liveEvents = new LiveEvents(driver);
    }


    @Test
    public void eventsWork(){
        // going to Events page and checking that at least one event is displayed
        try {
            Assert.assertTrue(homePage.selectEventsFromMenu().eventsAreDisplayed()
                    ,"Upcoming events are not displayed.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all displayed events take place today or in the future
        Assert.assertTrue(liveEvents.allEventsAreUpcoming()
                , "One or more events displayed are not upcoming.");
        // clicking Past Events button and checking that at least one event is displayed and all displayed events are in the past
        liveEvents.switchToPastEvents();
        Assert.assertTrue(liveEvents.eventsAreDisplayed(), "Past events are not displayed.");
        try {
            Assert.assertTrue(liveEvents.allEventsArePast(), "One or more events displayed are not past.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // opening the first event and checking that the Events Details module is displayed
        try{
            liveEvents.openFirstEvent();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        try{
            Assert.assertTrue(liveEvents.eventIsOpen(), "Event details have not been loaded.");
        }catch (TimeoutException e)
        {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }
}
