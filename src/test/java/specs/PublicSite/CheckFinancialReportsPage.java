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
public class CheckFinancialReportsPage extends AbstractSpec  {

    private final String Q4WebVersionNumber = "4.3.0.63";

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static HomePage homePage;
    private static FinancialReportsPage financialReportsPage;


    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);
        financialReportsPage = new FinancialReportsPage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

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
