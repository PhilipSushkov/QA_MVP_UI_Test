package specs.PreviewSite;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

/**
 * Created by easong on 1/24/17.
 */
public class MainPreviewPage extends AbstractSpec {

    //private final String Q4WebVersionNumber = "4.4.0.12";
    private String Q4WebVersionNumber;

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private CheckPublicSite publicTests = new CheckPublicSite();
    private static HomePage homePage;

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        Q4WebVersionNumber = propUIPublicSite.getProperty("siteVersion");
        System.out.println("Preview version number: "+ Q4WebVersionNumber);
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals(homePage.getVersionNumber(), Q4WebVersionNumber
                , "Displayed version number is incorrect");
    }
}
