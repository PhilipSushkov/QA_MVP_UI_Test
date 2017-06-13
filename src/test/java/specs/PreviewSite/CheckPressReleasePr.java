package specs.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.LivePressReleases;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

import java.time.Year;

/**
 * Created by easong on 1/24/17.
 */
public class CheckPressReleasePr extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private static HomePage homePage;
    private static LivePressReleases livePressReleases;

    private CheckPublicSite publicTests = new CheckPublicSite();

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
        livePressReleases = new LivePressReleases(driver);

    }

    @Test
    public void pressReleasesWork() {
        // going to Press Releases page and checking that at least one press release is displayed
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
        // clicking the first headline and checking the opened press release has a download link
        try {
            livePressReleases.openFirstPressRelease();
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue(livePressReleases.pressReleaseIsOpen()
                , "Press release is not open.");
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


