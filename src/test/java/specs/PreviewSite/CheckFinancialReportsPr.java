package specs.PreviewSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.FinancialReportsPage;
import pageobjects.LiveSite.HomePage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

/**
 * Created by easong on 1/24/17.
 */
public class CheckFinancialReportsPr extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\

    private CheckPublicSite publicTests = new CheckPublicSite();
    private static HomePage homePage;
    private static FinancialReportsPage financialReportsPage;


    @BeforeTest
    public void goToPreviewSite() throws Exception {
        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
        financialReportsPage = new FinancialReportsPage(driver);
        System.out.println("BeforeTest successfully Finished");
    }

    @Test
    public void financialReportsWork(){
        // going to Financial Reports page and checking that at least one report is displayed
        try {
            Assert.assertTrue(homePage.selectFinancialReportsFromMenu().financialReportsAreDisplayed()
                    , "Financial reports are not displayed.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all report titles are valid links
        Assert.assertTrue(financialReportsPage.reportTitlesAreLinks()
                , "One or more financial report titles are not links.");
        // checking that at least one report title links to a .pdf file
        Assert.assertTrue(financialReportsPage.pdfLinkIsPresent()
                , "No financial reports links to a .pdf file.");
    }

}
