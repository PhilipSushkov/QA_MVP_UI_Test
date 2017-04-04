package specs.PublicSite;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;


/**
 * Created by easong on 1/23/17.
 */
public class CheckMainPage extends AbstractSpec {

    //private final String Q4WebVersionNumber = "4.4.0.9";
    private String Q4WebVersionNumber;

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/


    private static HomePage homePage;



    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");
        Q4WebVersionNumber = propUIPublicSite.getProperty("siteVersion");

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals(homePage.getVersionNumber(), Q4WebVersionNumber
                , "Displayed version number is incorrect");
    }

}
