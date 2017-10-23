package specs.PublicSite;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import specs.AbstractSpec;

public class CheckPGEcorp extends AbstractSpec {
    private static final long DEFAULT_PAUSE = 2000;

    @BeforeTest
    public void goToPGEcorpSite() throws InterruptedException {
        driver.get("http://investor.pgecorp.com/");
        Thread.sleep(DEFAULT_PAUSE);
    }

    @Test
    public void checkStockPriceUpdates() throws InterruptedException {
        //Assert.assertEquals(homePage.getVersionNumber(), Q4WebVersionNumber, "Displayed version number is incorrect");

        String StockPriceOriginal = driver.findElement(By.xpath("//a[contains(@class, 'StockPrice')]")).getText();
        String StockDateOriginal = driver.findElement(By.xpath("//span[(@class='StockDate')]")).getText();

        Thread.sleep(DEFAULT_PAUSE);

        System.out.println("Stock Price Original: " + StockPriceOriginal);
        System.out.println("Stock Date Original: " + StockDateOriginal);

    }

}
