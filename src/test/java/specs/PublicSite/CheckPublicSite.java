package specs.PublicSite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.LiveSite.HomePage;
import specs.AbstractSpec;

public class CheckPublicSite extends AbstractSpec {

    private final String Q4WebVersionNumber = "4.2.1.64";

    @Before
    public void goToPublicSite(){
        driver.get("http://chicagotest.q4web.com");
        Assert.assertTrue("Home page of public site has not been loaded.", new HomePage(driver).logoIsPresent());
    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals("Displayed version number is incorrect", Q4WebVersionNumber, new HomePage(driver).getVersionNumber());
    }
}
