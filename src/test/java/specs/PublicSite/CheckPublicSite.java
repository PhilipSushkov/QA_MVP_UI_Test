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
        int xStart1Month = new StockInformationPage(driver).getChartSliderXStart();
        new StockInformationPage(driver).switchChartTo1Quarter();
        int xStart1Quarter = new StockInformationPage(driver).getChartSliderXStart();
        new StockInformationPage(driver).switchChartTo1Year();
        int xStart1Year = new StockInformationPage(driver).getChartSliderXStart();
        Assert.assertTrue("Stock chart period is not switching properly.\nxStart1Year="+xStart1Year+"\nxStart1Quarter="+xStart1Quarter+"\nxStart1Month="+xStart1Month,
                xStart1Month>xStart1Quarter && xStart1Quarter>xStart1Year);
        Assert.assertTrue("Hovering over chart doesn't work.", new StockInformationPage(driver).canHoverOverChart());
    }
}
