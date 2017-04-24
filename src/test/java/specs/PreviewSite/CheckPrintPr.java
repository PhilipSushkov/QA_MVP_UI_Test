package specs.PreviewSite;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.PrintPreviewPage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by sarahr on 4/12/2017.
 */
public class CheckPrintPr extends AbstractSpec {
    //This is the Preview class for the print preview tests
    //Couldn't keep normal naming conditions as it would be PrintPreviewPreviewPage and that's weird

    private static HomePage homePage;

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
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
