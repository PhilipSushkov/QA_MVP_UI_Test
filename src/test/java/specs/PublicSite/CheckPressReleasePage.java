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
public class CheckPressReleasePage extends AbstractSpec {

    private final String Q4WebVersionNumber = "4.4.0.9";

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static HomePage homePage;
    private static LivePressReleases livePressReleases;


    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);
        livePressReleases = new LivePressReleases(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }


    @Test
    public void pressReleaseExist() {
        // going to Press Releases page and checking that at least one press release is displayed
        try {
            Assert.assertTrue(homePage.selectPressReleasesFromMenu().pressReleasesAreDisplayed()
                    , "Press releases are not displayed.");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }


    }

    @Test
    public void pressReleaseDateFiltersWork() {
        // First navigate to the releases page and make sure at least 1 press release is present
        try {
            Assert.assertTrue(homePage.selectPressReleasesFromMenu().pressReleasesAreDisplayed()
                    , "Press releases are not displayed.");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all press releases displayed are from the current year
        Assert.assertTrue(livePressReleases.pressReleasesAreAllFromYear(Year.now().toString())
                , "One or more displayed press releases are not from the current year.");
        // switching year to 2015 and checking that all press releases displayed are from 2015
        livePressReleases.switchYearTo("2015");
        Assert.assertTrue(livePressReleases.pressReleasesAreAllFromYear("2015")
                , "One or more displayed press releases are not from the selected year (2015).");
    }


    @Test
    public void PressReleaseDownloadWork() {
        // First navigate to the releases page and make sure at least 1 press release is present
        try {
            Assert.assertTrue(homePage.selectPressReleasesFromMenu().pressReleasesAreDisplayed()
                    , "Press releases are not displayed.");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // Clicking the first link to make sure there is a download link
        try {
            livePressReleases.openFirstPressRelease();
            Assert.assertTrue(livePressReleases.FindDownloadLink(),"Download Link Unavailable");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

    }
}


