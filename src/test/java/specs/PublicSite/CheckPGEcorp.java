package specs.PublicSite;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import specs.AbstractSpec;

import java.util.Date;

public class CheckPGEcorp extends AbstractSpec {
    private static final long DEFAULT_PAUSE = 2000;

    @BeforeTest
    public void goToPGEcorpSite() throws InterruptedException {
        driver.get("http://investor.pgecorp.com/");
        //driver.get("http://first-quantum.com/");
        Thread.sleep(DEFAULT_PAUSE);
    }

    @Test (threadPoolSize = 1, invocationCount = 20)
    public void checkStockPriceUpdates() throws InterruptedException {
        //Assert.assertEquals(homePage.getVersionNumber(), Q4WebVersionNumber, "Displayed version number is incorrect");

        // Original data
        String StockPriceOriginal = driver.findElement(By.xpath("//a[contains(@class, 'StockPrice')]")).getText();
        //String StockPriceOriginal = driver.findElement(By.xpath("//a[(@class='StockPrice')]|//a/span[contains(@id, 'lblPrice')]|//a[contains(@id, 'hrefPrice')]")).getAttribute("innerText");
        String StockDateOriginal = driver.findElement(By.xpath("//span[(@class='StockDate')]")).getText();
        //String StockDateOriginal = driver.findElement(By.xpath("//span[contains(@id, 'lblTradeDate')]")).getText();

        Thread.sleep(DEFAULT_PAUSE);

        System.out.println(" --- START --- ");

        System.out.println(new Date());
        System.out.println("Stock Price Original: " + StockPriceOriginal);
        //System.out.println("Stock Date Original: " + StockDateOriginal);
        System.out.println(" ------ ");

        Thread.sleep(1500000);

        driver.navigate().refresh();

        // Updated data
        String StockPriceUpdated = driver.findElement(By.xpath("//a[contains(@class, 'StockPrice')]")).getText();
        String StockDateUpdated = driver.findElement(By.xpath("//span[(@class='StockDate')]")).getText();

        Thread.sleep(DEFAULT_PAUSE);

        System.out.println(new Date());
        System.out.println("Stock Price Updated: " + StockPriceUpdated);
        System.out.println("Stock Date Updated: " + StockDateUpdated);
        System.out.println(" ------ ");


        Assert.assertNotEquals(StockPriceOriginal, StockPriceUpdated, "Stock Price value shouldn't be equal");
        //Assert.assertNotEquals(StockDateOriginal, StockDateUpdated, "Stock Date value shouldn't be equal");
        System.out.println(" --- FINISH --- ");


    }


    @AfterTest
    public void tearDown() {
    }


}
