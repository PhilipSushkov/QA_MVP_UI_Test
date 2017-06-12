package specs.PublicSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import pageobjects.api.historical.HistoricalStockQuote;
import pageobjects.api.historical.Q4Dataset;
import pageobjects.api.historical.QuandlConnectToApi;
import pageobjects.api.historical.QuandlDataset;
import pageobjects.api.login.Auth;
import specs.AbstractSpec;
import specs.ApiAbstractSpec;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.fail;
/**
 * Created by easong on 1/23/17.
 */

public class CheckStockInformationPage extends AbstractSpec {

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckSitePr.java \\\\

    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
     Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static StockInformationPage stockInformationPage;
    private static HomePage homePage;
    private static final String STAGING_ENV = "Staging_Env";


    @BeforeTest
    public void goToPublicSite() throws IOException{
        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        //Assert.assertTrue(new Auth().getAccessToken(STAGING_ENV), "Access Token didn't receive");

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
        Q4Dataset stockQuote;
        try {
            HistoricalStockQuote historicalStockQuote = new HistoricalStockQuote(STAGING_ENV);
            stockQuote = historicalStockQuote.getHistoricalQuote();
        }catch (IOException e){
            fail("Problem retrieving stock data from Q4 api.");
            return;
        }
        // checking that displayed stock quote values are close to values from Yahoo
        Assert.assertEquals(stockInformationPage.getStockPrice(),stockQuote.getPrice()
               , 0.25, "Stock price isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockChange(), stockQuote.getChange()
                , 0.25,"Stock change isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockPChange(), stockQuote.getChangeInPercent()
                , 1, "Stock % change isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockDayHigh(), stockQuote.getDayHigh()
                , 0.25, "Stock intraday high isn't accurate");
        Assert.assertEquals(stockInformationPage.getStock52WeekHigh(), stockQuote.getYearHigh()
                , 0.1, "Stock 52 week high isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockDayLow(), stockQuote.getDayLow()
                , 0.25, "Stock intraday low isn't accurate");
        Assert.assertEquals(stockInformationPage.getStock52WeekLow(), stockQuote.getYearLow()
                , 0.25, "Stock 52 week low isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockTodayOpen(), stockQuote.getOpen()
                , 0.01, "Stock today's open isn't accurate");
        Assert.assertEquals(stockInformationPage.getStockPreviousClose(), stockQuote.getPreviousClose()
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
            Date today = lastTradingDay.getTime();
            // Format the date so QuandlAPI can read it
            DateFormat todaysDate = new SimpleDateFormat("yyyy-MM-dd");
            String inputDate = todaysDate.format(today);
            QuandlDataset lastTradingDayQuotes;
            System.out.println(lastTradingDay.getTime());
            // fetching data from Quandl
            try {
                // Check if Quandl works well
                lastTradingDayQuotes = QuandlConnectToApi.getDatasetBetweenDates("TXRH", inputDate, inputDate);

            } catch (Exception e) {
                fail("Problem retrieving last trading day stock data from Yahoo Finance.");
                return;
            }
            // comparing Quandl data with displayed values
            Assert.assertEquals(stockInformationPage.getHistoricalHigh(), Double.parseDouble(lastTradingDayQuotes.getHighPrices().get(0))
                    , 0.01, "Last trading day high isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalLow(), Double.parseDouble(lastTradingDayQuotes.getLowPrices().get(0))
                    , 0.01, "Last trading day low isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalVolume()
                    , Double.parseDouble(lastTradingDayQuotes.getVolume().get(0))
                    , 10000, "Last trading day volume isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalOpen(), Double.parseDouble(lastTradingDayQuotes.getOpenPrices().get(0))
                    , 0.01, "Last trading day opening price isn't accurate");
            Assert.assertEquals(stockInformationPage.getHistoricalLast(), Double.parseDouble(lastTradingDayQuotes.getClosingPrices().get(0))
                    , 0.01, "Last trading day last price isn't accurate" );


            // checking historical values from older day
            stockInformationPage.changeQuoteDate();
            Calendar olderDay = stockInformationPage.getCurrentDate(); //getting date from date dropdowns in module
            Date olderTradingDay = olderDay.getTime();
            // Format the date so QuandlAPI can read it
            DateFormat olderDate = new SimpleDateFormat("yyyy-MM-dd");
            String olderInputDate = olderDate.format(olderTradingDay);
            QuandlDataset olderDayQuotes;
            // fetching data from Quandl
            try {
                olderDayQuotes = QuandlConnectToApi.getDatasetBetweenDates("TXRH", olderInputDate, olderInputDate);
            } catch (Exception e) {
                fail("Problem retrieving older day stock data from Quandl.");
                return;
            }
            // comparing Yahoo data with displayed values
            Assert.assertEquals(stockInformationPage.getHistoricalHigh(), Double.parseDouble(olderDayQuotes.getHighPrices().get(0))
                    ,0.01, "Older day high isn't accurate");

            Assert.assertEquals(stockInformationPage.getHistoricalLow(),Double.parseDouble(olderDayQuotes.getLowPrices().get(0))
                    , 0.01, "Older day low isn't accurate");

            Assert.assertEquals(stockInformationPage.getHistoricalVolume(), Double.parseDouble(olderDayQuotes.getVolume().get(0))
                    , 5000, "Older day volume isn't accurate");

            Assert.assertEquals(stockInformationPage.getHistoricalOpen(),Double.parseDouble(olderDayQuotes.getOpenPrices().get(0))
                    , 0.01, "Older day opening price isn't accurate" );

            Assert.assertEquals(stockInformationPage.getHistoricalLast(), Double.parseDouble(olderDayQuotes.getClosingPrices().get(0))
                    , 0.01, "Older day last price isn't accurate");

        }catch (TimeoutException e)
        {
            e.printStackTrace();
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

}
