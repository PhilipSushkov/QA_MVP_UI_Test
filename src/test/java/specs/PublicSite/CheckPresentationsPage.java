package specs.PublicSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;

import java.time.Year;

/**
 * Created by easong on 1/23/17.
 */
public class CheckPresentationsPage extends AbstractSpec {

    private final String Q4WebVersionNumber = "4.4.0.9";

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static HomePage homePage;
    private static LivePresentations livePresentations;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);
        livePresentations = new LivePresentations(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void presentationCurrentDateCheck(){
        // going to Presentations page and checking that at least one presentation is displayed
        try{
            Assert.assertTrue(homePage.selectPresentationsFromMenu().presentationsAreDisplayed()
                    , "Presentations are not displayed.");
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all presentations displayed are from the current year
        Assert.assertTrue(livePresentations.presentationsAreAllFromYear(Year.now().toString())
                , "One or more displayed presentations are not from the current year.");
    }

    @Test
    public void PresentationDateFilterCheck(){
        // going to Presentations page and checking that at least one presentation is displayed
        try{
            Assert.assertTrue(homePage.selectPresentationsFromMenu().presentationsAreDisplayed()
                    , "Presentations are not displayed.");
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // switching year to 2015 and checking that all presentations displayed are from 2015
        livePresentations.switchYearTo("2015");
        Assert.assertTrue(livePresentations.presentationsAreAllFromYear("2015")
                , "One or more displayed presentations are not from the selected year (2015).");
    }

    @Test
    public void presentationLinkCheck(){
        // going to Presentations page and checking that at least one presentation is displayed
        try{
            Assert.assertTrue(homePage.selectPresentationsFromMenu().presentationsAreDisplayed()
                    , "Presentations are not displayed.");
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all presentation links are valid links
        Assert.assertTrue(livePresentations.presentationLinksAreLinks()
                , "One or more presentation links are not links.");
    }

    @Test
    public void presentationsPDFPresent(){
        // going to Presentations page and checking that at least one presentation is displayed
        try{
            Assert.assertTrue(homePage.selectPresentationsFromMenu().presentationsAreDisplayed()
                    , "Presentations are not displayed.");
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that at least one link links to a .pdf file
        Assert.assertTrue(livePresentations.pdfLinkIsPresent()
                ,"No presentations link to a .pdf file.");
    }


}
