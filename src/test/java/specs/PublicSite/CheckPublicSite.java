package specs.PublicSite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.LiveSite.HomePage;
import pageobjects.Page;
import pageobjects.LiveSite.StockInformationPage;
import specs.AbstractSpec;

public class CheckPublicSite extends AbstractSpec {
    private final String Q4WebVersionNumber = "4.2.1.64";

    @Before
    public void goToPublicSite() {
        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        Assert.assertTrue("Home page of public site has not been loaded.", new HomePage(driver).logoIsPresent());
    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals("Displayed version number is incorrect", Q4WebVersionNumber, new HomePage(driver).getVersionNumber());
    }

    @Test
    public void stockChartWorks(){
        Assert.assertTrue("Stock chart is not displayed", new HomePage(driver).selectStockInformationFromMenu().stockChartIsDisplayed());
        new StockInformationPage(driver).switchChartTo1Month();
        Assert.assertEquals("Stock chart is not displaying one month", "366", new StockInformationPage(driver).getChartSliderXStart());
        new StockInformationPage(driver).switchChartTo1Quarter();
        Assert.assertEquals("Stock chart is not displaying one quarter", "338", new StockInformationPage(driver).getChartSliderXStart());
        new StockInformationPage(driver).switchChartTo1Year();
        Assert.assertEquals("Stock chart is not displaying one year", "211", new StockInformationPage(driver).getChartSliderXStart());
        Assert.assertTrue("Hovering over chart doesn't work.", new StockInformationPage(driver).canHoverOverChart());
    }
}
