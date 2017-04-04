package specs.PublicSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.Calendar;

        import static org.testng.Assert.fail;
/**
 * Created by easong on 1/23/17.
 */

public class CheckStockInformationPage extends AbstractSpec{

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\

    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static StockInformationPage stockInformationPage;
    private static HomePage homePage;

    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        stockInformationPage = new StockInformationPage(driver);
        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void stockChartXigniteWorks(){
        // going to Stock Information page and checking that Xignite stock chart appears
        Assert.assertTrue(homePage.selectStockInformationFromMenu().stockChartXigniteIsDisplayed()
                , "Xignite stock chart is not displayed.");

        // trying to switch time range and checking that the longer the time range, the earlier the start of the range displayed (x position of left side of slider)
        stockInformationPage.switchChartXigniteTo1Month();
        int xStart1Month = stockInformationPage.getChartXigniteSliderXStart();
        stockInformationPage.switchChartXigniteTo1Quarter();
        int xStart1Quarter = stockInformationPage.getChartXigniteSliderXStart();
        stockInformationPage.switchChartXigniteTo1Year();
        int xStart1Year = stockInformationPage.getChartXigniteSliderXStart();
        Assert.assertTrue(xStart1Month>xStart1Quarter && xStart1Quarter>xStart1Year
                , "Xignite stock chart period is not switching properly.\nxStart1Year="+xStart1Year+
                        "\nxStart1Quarter="+xStart1Quarter+"\nxStart1Month="+xStart1Month);

        // checking that hovering over the chart causes hovertext to appear
        Assert.assertTrue(stockInformationPage.canHoverOverChartXignite()
                , "Hovering over Xignite chart doesn't work.");
    }

    @Test
    public void stockQuoteWorks(){
        // going to Stock Information page and checking that the stock quote module is displayed
        Assert.assertTrue(homePage.selectStockInformationFromMenu().stockQuoteIsDisplayed()
                , "Stock quote is not displayed.");

        // checking that all stock quote values are present and doing basic validation (like checking that stock prices are above zero)
        Assert.assertTrue(stockInformationPage.stockQuoteValuesArePresent()
                , "One or more stock quote values are missing or invalid.");
    }

    @Test
    public void stockQuoteValuesAreAccurate() throws TimeoutException{
        // going to Stock Information page and checking that the stock quote module is displayed
        Assert.assertTrue(homePage.selectStockInformationFromMenu().stockQuoteIsDisplayed()
                , "Stock quote is not displayed.");

        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);

        // fetching stock quotes from Yahoo
        StockQuote stockQuote;
        try {
            stockQuote = YahooFinance.get("TXRH").getQuote(false);
        }catch (IOException e){
            fail("Problem retrieving stock data from Yahoo Finance.");
            return;
        }
        // checking that displayed stock quote values are close to values from Yahoo
        Assert.assertEquals(stockInformationPage.getStockPrice(),stockQuote.getPrice().doubleValue()
                , 0.25, "Stock price isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockChange(), stockQuote.getChange().doubleValue()
                , 0.25,"Stock change isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockPChange(), stockQuote.getChangeInPercent().doubleValue()
                , 1, "Stock % change isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockDayHigh(), stockQuote.getDayHigh().doubleValue()
                , 0.25, "Stock intraday high isn't accurate");
        Assert.assertEquals(stockInformationPage.getStock52WeekHigh(), stockQuote.getYearHigh().doubleValue()
                , 0.1, "Stock 52 week high isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockDayLow(), stockQuote.getDayLow().doubleValue()
                , 0.25, "Stock intraday low isn't accurate");
        Assert.assertEquals(stockInformationPage.getStock52WeekLow(), stockQuote.getYearLow().doubleValue()
                , 0.25, "Stock 52 week low isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockTodayOpen(), stockQuote.getOpen().doubleValue()
                , 0.01, "Stock today's open isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockPreviousClose(), stockQuote.getPreviousClose().doubleValue()
                , 0.01, "Stock previous close isn't accurate");
        // Accuracy of volume is not checked due to the wide tolerance (in the hundreds of thousands) that would be needed to account for delayed values.
    }

    @Test
    public void stockChartTickertechWorks() {
        /*
            The actual chart in the Tickertech module is a single image file that is loaded from Tickertech's website
            with its URL containing all the parameters that affect the chart. This test extracts these parameters from
            the URL in order to confirm that clicking various things results in the right chart being requested from Tickertech.
            PARAMETERS (the ones that change when modifying the chart):
                period: 1m, 1q, 1y, 5y, 1mp, 1qp, 1yp, 5yp (p if chart set to % change)
                fillBelowLine (Chart type): yes (mountain), prev (fill to prev. close), no (line, point, bar, candle stick)
                type (Chart type): line (mountain, fill to prev. close, line), point (point), bar (bar), candle (candle stick)
                showHighsLows (Chart type): no (mountain, fill to prev. close, line, bar, candle stick), yes (point)
                showVolumeTable: no if comparing
                showLegendTable: yes if comparing
                symbols: TXRH (not comparing)
        		         TXRH,$DJI (comparing with Dow)
		                 TXRH,$DJI,COMP (comparing with Dow and Nasdaq)
        		         TXRH,$DJI,COMP,SPX (comparing with Dow, Nasdaq, and S&P)
		                 TXRH,$DJI,COMP,SPX,RUT (comparing with all)
        		         TXRH,COMP,RUT (comparing with Nasdaq and Russell)
                Note: candle stick can’t be used with % change
                Note: upon activating compare, chart switches to % change and line
        */
        try {
            //go to stock information page and check that chart is present
            Assert.assertTrue(homePage.selectStockInformationFromMenu().stockChartTickertechIsDisplayed()
                    , "Tickertech stock chart is not displayed." );
            //cycle through time periods and check that chart changes accordingly
            stockInformationPage.switchChartTickertechTo1Month();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("period=1m&"),
                    "Tickertech chart isn't displaying 1 month period" );
            stockInformationPage.switchChartTickertechTo1Quarter();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("period=1q&")
                    , "Tickertech chart isn't displaying 1 quarter period");
            stockInformationPage.switchChartTickertechTo1Year();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("period=1y&")
                    , "Tickertech chart isn't displaying 1 year period");
            //try switching to % change and check that chart changes accordingly
            stockInformationPage.switchChartTickertechToPChange();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("period=1yp&")
                    , "Tickertech chart isn't displaying % change");
            //try switching back to price and check that chart changes accordingly
            stockInformationPage.switchChartTickertechToPrice();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("period=1y&")
                    , "Tickertech chart isn't displaying price");
            //cycle though chart types and check that chart changes accordingly
            stockInformationPage.switchChartTickertechToFillToPrevClose();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("fillBelowLine=prev&")
                    , "Tickertech chart isn't displaying fill to prev. close type");
            stockInformationPage.switchChartTickertechToLine();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("fillBelowLine=no&")
                    , "Tickertech chart isn't displaying line type");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("type=line&")
                    , "Tickertech chart isn't displaying line type");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("showHighsLows=no&")
                    , "Tickertech chart isn't displaying line type");
            stockInformationPage.switchChartTickertechToPoint();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("type=point&")
                    , "Tickertech chart isn't displaying point type");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("showHighsLows=yes&")
                    , "Tickertech chart isn't displaying point type");
            stockInformationPage.switchChartTickertechToBar();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("type=bar&")
                    , "Tickertech chart isn't displaying bar type");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("showHighsLows=no&")
                    , "Tickertech chart isn't displaying bar type" );
            stockInformationPage.switchChartTickertechToCandleStick();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("type=candle&")
                    , "Tickertech chart isn't displaying candle stick type" );
            stockInformationPage.switchChartTickertechToMountain();
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("fillBelowLine=yes&")
                    , "Tickertech chart isn't displaying mountain type" );
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("type=line&")
                    , "Tickertech chart isn't displaying mountain type" );
            //select one or more indices/stocks and check that chart changes accordingly
            stockInformationPage.tickertechCompareVs("",true, false, true, false);
            // ^^^ comparing with Dow and S&P
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("showVolumeTable=no&")
                    , "Tickertech chart isn't comparing with indices properly");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("showLegendTable=yes&")
                    , "Tickertech chart isn't comparing with indices properly");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("period=1yp&")
                    , "Tickertech chart isn't comparing with indices properly");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("fillBelowLine=no&")
                    , "Tickertech chart isn't comparing with indices properly");
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("symbols=TXRH,$DJI,SPX&")
                    , "Tickertech chart isn't displaying Dow and S&P only");
            stockInformationPage.tickertechCompareVs("", true, true, true, true);
            // ^^^ comparing with all four indices
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("symbols=TXRH,$DJI,COMP,SPX,RUT&")
                    , "Tickertech chart isn't displaying all four indices");
            stockInformationPage.tickertechCompareVs("", false, true, false, false);
            // ^^^ comparing with Nasdaq
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("symbols=TXRH,COMP&")
                    , "Tickertech chart isn't displaying Nasdaq only");
            stockInformationPage.tickertechCompareVs("AAPL", false, true, false, false);
            // ^^^ comparing with AAPL and Nasdaq
            Assert.assertTrue(stockInformationPage.getTickertechSRC().contains("symbols=TXRH,AAPL,COMP&")
                    , "Tickertech chart isn't displaying AAPL and Nasdaq only");

        }
        catch (TimeoutException e){
            e.printStackTrace();
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void historicalQuoteValuesAreAccurate() {
        try {
            // going to Stock Information page and checking that historical quotes module appears
            Assert.assertTrue(homePage.selectStockInformationFromMenu().historicalQuotesAreDisplayed()
                    , "Historical quotes are not displayed." );

            // checking historical values from last trading day
            Calendar lastTradingDay = stockInformationPage.getCurrentDate(); //getting date from date dropdowns in module
            HistoricalQuote lastTradingDayQuotes;
            // fetching data from Yahoo
            try {
                lastTradingDayQuotes = YahooFinance.get("TXRH").getHistory(lastTradingDay, lastTradingDay
                        , Interval.DAILY).get(0);
            } catch (IOException e) {
                fail("Problem retrieving last trading day stock data from Yahoo Finance.");
                return;
            }
            // comparing Yahoo data with displayed values
            Assert.assertEquals(stockInformationPage.getHistoricalHigh(), lastTradingDayQuotes.getHigh().doubleValue()
                    , 0.01, "Last trading day high isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalLow(), lastTradingDayQuotes.getLow().doubleValue()
                    , 0.01, "Last trading day low isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalVolume()
                    , lastTradingDayQuotes.getVolume().doubleValue()
                    , 10000, "Last trading day volume isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalOpen(), lastTradingDayQuotes.getOpen().doubleValue()
                    , 0.01, "Last trading day opening price isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalLast(), lastTradingDayQuotes.getClose().doubleValue()
                    , 0.01, "Last trading day last price isn't accurate" );

            // checking historical values from older day
            stockInformationPage.changeQuoteDate();
            Calendar olderDay = stockInformationPage.getCurrentDate(); //getting date from date dropdowns in module
            HistoricalQuote olderDayQuotes;
            // fetching data from Yahoo
            try {
                olderDayQuotes = YahooFinance.get("TXRH").getHistory(olderDay, olderDay, Interval.DAILY).get(0);
            } catch (IOException e) {
                fail("Problem retrieving older day stock data from Yahoo Finance.");
                return;
            }
            // comparing Yahoo data with displayed values
            Assert.assertEquals(stockInformationPage.getHistoricalHigh(), olderDayQuotes.getHigh().doubleValue()
                    ,0.01, "Older day high isn't accurate");

            Assert.assertEquals(stockInformationPage.getHistoricalLow(),olderDayQuotes.getLow().doubleValue()
                    , 0.01, "Older day low isn't accurate");

            Assert.assertEquals(stockInformationPage.getHistoricalVolume(), olderDayQuotes.getVolume().doubleValue()
                    , 5000, "Older day volume isn't accurate");

            Assert.assertEquals(stockInformationPage.getHistoricalOpen(),olderDayQuotes.getOpen().doubleValue()
                    , 0.01, "Older day opening price isn't accurate" );

            Assert.assertEquals(stockInformationPage.getHistoricalLast(), olderDayQuotes.getClose().doubleValue()
                    , 0.01, "Older day last price isn't accurate");

        }catch (TimeoutException e)
        {
            e.printStackTrace();
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

}
