package specs.PublicSite;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.PrintPreviewPage;
import specs.AbstractSpec;

/**
 * Created by sarahr on 4/11/2017.
 */
public class CheckPrintPreview extends AbstractSpec{

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void checkFor200ResponseCode() {
        //makes sure that their is no errors when loading the page

        //go to a page that has a print preview button
        homePage.selectFAQFromMenu();
        PrintPreviewPage printPreview = new PrintPreviewPage(driver);
        printPreview.clickPrintPreviewBtn();
        String link = driver.getCurrentUrl();
        printPreview.checkHTTPResponse(link);
    }

    //This sees if the ContentPane has the same text as the print preview
    //This test is not dependant on the content of the page
    @Test
    public void printWorks(){
        homePage.selectFAQFromMenu();
        PrintPreviewPage print = new PrintPreviewPage(driver);
        //check to see if the title on the main page is the same on the print preview
        print.clickPrintPreviewBtn();
        print.contentIsSameOnPP();
    }

}
