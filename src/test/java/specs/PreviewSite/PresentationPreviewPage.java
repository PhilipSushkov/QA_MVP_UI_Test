package specs.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.LivePresentations;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

import java.time.Year;

/**
 * Created by easong on 1/24/17.
 */
public class PresentationPreviewPage extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private CheckPublicSite publicTests = new CheckPublicSite();
    private static HomePage homePage;
    private static LivePresentations livePresentations;


    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
        livePresentations = new LivePresentations(driver);
    }

    @Test
    public void presentationsWork(){
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
        // switching year to 2015 and checking that all presentations displayed are from 2015
        livePresentations.switchYearTo("2015");
        Assert.assertTrue(livePresentations.presentationsAreAllFromYear("2015")
                , "One or more displayed presentations are not from the selected year (2015).");
        // checking that all presentation links are valid links
        Assert.assertTrue(livePresentations.presentationLinksAreLinks()
                , "One or more presentation links are not links.");
        // checking that at least one link links to a .pdf file
        Assert.assertTrue(livePresentations.pdfLinkIsPresent()
                    ,"No presentations link to a .pdf file.");
        }



}
