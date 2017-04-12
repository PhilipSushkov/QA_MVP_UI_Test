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
        String link = "https://chicagotest.q4web.com/English/Investors/test12/default.aspx?print=1";
        PrintPreviewPage print = new PrintPreviewPage(driver);
        print.checkHTTPResponse(link);
    }

    //test to make sure that the print preview actually appears
    //Question is, how can this be universal for all browsers?
    @Test
    public void printWorks(){
        PrintPreviewPage print = new PrintPreviewPage(driver);
    }

}
