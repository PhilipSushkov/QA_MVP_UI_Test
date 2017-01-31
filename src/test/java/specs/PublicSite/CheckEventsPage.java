package specs.PublicSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;


/**
 * Created by easong on 1/23/17.
 */
public class CheckEventsPage extends AbstractSpec {

    private final String Q4WebVersionNumber = "4.3.0.63";

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/


    private static HomePage homePage;
    private static LiveEvents liveEvents;


    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);
        liveEvents = new LiveEvents(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

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
