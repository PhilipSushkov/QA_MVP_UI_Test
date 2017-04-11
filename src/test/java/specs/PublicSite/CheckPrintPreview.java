package specs.PublicSite;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import specs.AbstractSpec;
import java.net.*;
import java.net.HttpURLConnection;

/**
 * Created by sarahr on 4/11/2017.
 */
public class CheckPrintPreview extends AbstractSpec{

    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void checkFor200ResponseCode(){
        //makes sure that their is no errors when loading the page
        String link = "https://chicagotest.q4web.com/English/Investors/test12/default.aspx?print=1";
        int code = 0;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            code = connection.getResponseCode();
        }
        catch(Exception e){
            System.out.println(e);
        }

        Assert.assertEquals(code, 200,"Response code is not 200");
    }

}
