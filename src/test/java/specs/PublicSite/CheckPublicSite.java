package specs.PublicSite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobjects.LiveSite.HomePage;
import pageobjects.Page;
import pageobjects.LiveSite.StockInformationPage;
import specs.AbstractSpec;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.util.Calendar;

import static org.junit.Assert.fail;

public class CheckPublicSite extends AbstractSpec {
    private final String Q4WebVersionNumber = "4.2.1.64";

    @Before
    public void goToPublicSite() {
        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");
        Assert.assertTrue("Home page of public site has not been loaded.", new HomePage(driver).logoIsPresent());
    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals("Displayed version number is incorrect", Q4WebVersionNumber, new HomePage(driver).getVersionNumber());
    }

    @Test
    public void stockChartWorks(){
        Assert.assertTrue("Stock chart is not displayed.", new HomePage(driver).selectStockInformationFromMenu().stockChartIsDisplayed());
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

    @Test
    public void stockQuoteWorks(){
        Assert.assertTrue("Stock quote is not displayed.", new HomePage(driver).selectStockInformationFromMenu().stockQuoteIsDisplayed());
        Assert.assertTrue("One or more stock quote values are missing or invalid.", new StockInformationPage(driver).stockQuoteValuesArePresent());
    }

    @Test
    public void stockQuoteValuesAreAccurate(){
        Assert.assertTrue("Stock quote is not displayed.", new HomePage(driver).selectStockInformationFromMenu().stockQuoteIsDisplayed());
        StockQuote stockQuote;
        try {
            stockQuote = YahooFinance.get("TXRH").getQuote(false);
        }catch (IOException e){
            fail("Problem retrieving stock data from Yahoo Finance.");
            return;
        }
        Assert.assertEquals("Stock price isn't accurate", stockQuote.getPrice().doubleValue(), new StockInformationPage(driver).getStockPrice(), 0.1);
        Assert.assertEquals("Stock change isn't accurate", stockQuote.getChange().doubleValue(), new StockInformationPage(driver).getStockChange(), 0.1);
        Assert.assertEquals("Stock % change isn't accurate", stockQuote.getChangeInPercent().doubleValue(), new StockInformationPage(driver).getStockPChange(), 0.5);
        Assert.assertEquals("Stock intraday high isn't accurate", stockQuote.getDayHigh().doubleValue(), new StockInformationPage(driver).getStockDayHigh(), 0.1);
        Assert.assertEquals("Stock 52 week high isn't accurate", stockQuote.getYearHigh().doubleValue(), new StockInformationPage(driver).getStock52WeekHigh(), 0.1);
        Assert.assertEquals("Stock intraday low isn't accurate", stockQuote.getDayLow().doubleValue(), new StockInformationPage(driver).getStockDayLow(), 0.1);
        Assert.assertEquals("Stock 52 week low isn't accurate", stockQuote.getYearLow().doubleValue(), new StockInformationPage(driver).getStock52WeekLow(), 0.1);
        Assert.assertEquals("Stock today's open isn't accurate", stockQuote.getOpen().doubleValue(), new StockInformationPage(driver).getStockTodayOpen(), 0.01);
        Assert.assertEquals("Stock previous close isn't accurate", stockQuote.getPreviousClose().doubleValue(), new StockInformationPage(driver).getStockPreviousClose(), 0.01);
    }

    @Test
    public void historicalQuotesWork(){
        Assert.assertTrue("Historical quotes are not displayed.", new HomePage(driver).selectStockInformationFromMenu().historicalQuotesAreDisplayed());
        Assert.assertTrue("One or more historical quote values are missing or invalid.", new StockInformationPage(driver).historicalQuoteValuesArePresent());
        String[] lastDayQuotes = new StockInformationPage(driver).getHistoricalQuote();
        new StockInformationPage(driver).changeQuoteDate();
        Assert.assertTrue("One or more historical quote values are missing or invalid after changing date.", new StockInformationPage(driver).historicalQuoteValuesArePresent());
        String[] olderDayQuotes = new StockInformationPage(driver).getHistoricalQuote();
        for (int i=0; i<olderDayQuotes.length; i++){
            Assert.assertFalse("Identical value ("+lastDayQuotes[i]+" = "+olderDayQuotes[i]+") present in last trading day's historical quote and older (May 25th, 2016) historical quote.",
                    olderDayQuotes[i].equals(lastDayQuotes[i]));
        }
    }

    @Test
    public void historicalQuoteValuesAreAccurate(){
        Assert.assertTrue("Historical quotes are not displayed.", new HomePage(driver).selectStockInformationFromMenu().historicalQuotesAreDisplayed());

        // checking historical values from last trading day
        Calendar lastTradingDay = new StockInformationPage(driver).getCurrentDate();
        HistoricalQuote lastTradingDayQuotes;
        try {
            lastTradingDayQuotes = YahooFinance.get("TXRH").getHistory(lastTradingDay, lastTradingDay, Interval.DAILY).get(0);
        }catch (IOException e){
            fail("Problem retrieving last trading day stock data from Yahoo Finance.");
            return;
        }
        Assert.assertEquals("Last trading day high isn't accurate", lastTradingDayQuotes.getHigh().doubleValue(), new StockInformationPage(driver).getHistoricalHigh(), 0.01);
        Assert.assertEquals("Last trading day low isn't accurate", lastTradingDayQuotes.getLow().doubleValue(), new StockInformationPage(driver).getHistoricalLow(), 0.01);
        Assert.assertEquals("Last trading day volume isn't accurate", lastTradingDayQuotes.getVolume().doubleValue(), new StockInformationPage(driver).getHistoricalVolume(), 5000);
        Assert.assertEquals("Last trading day opening price isn't accurate", lastTradingDayQuotes.getOpen().doubleValue(), new StockInformationPage(driver).getHistoricalOpen(), 0.01);
        Assert.assertEquals("Last trading day last price isn't accurate", lastTradingDayQuotes.getClose().doubleValue(), new StockInformationPage(driver).getHistoricalLast(), 0.01);

        // checking historical values from older day
        new StockInformationPage(driver).changeQuoteDate();
        Calendar olderDay = new StockInformationPage(driver).getCurrentDate();
        HistoricalQuote olderDayQuotes;
        try {
            olderDayQuotes = YahooFinance.get("TXRH").getHistory(olderDay, olderDay, Interval.DAILY).get(0);
        }catch (IOException e){
            fail("Problem retrieving older day stock data from Yahoo Finance.");
            return;
        }
        Assert.assertEquals("Older day high isn't accurate", olderDayQuotes.getHigh().doubleValue(), new StockInformationPage(driver).getHistoricalHigh(), 0.01);
        Assert.assertEquals("Older day low isn't accurate", olderDayQuotes.getLow().doubleValue(), new StockInformationPage(driver).getHistoricalLow(), 0.01);
        Assert.assertEquals("Older day volume isn't accurate", olderDayQuotes.getVolume().doubleValue(), new StockInformationPage(driver).getHistoricalVolume(), 5000);
        Assert.assertEquals("Older day opening price isn't accurate", olderDayQuotes.getOpen().doubleValue(), new StockInformationPage(driver).getHistoricalOpen(), 0.01);
        Assert.assertEquals("Older day last price isn't accurate", olderDayQuotes.getClose().doubleValue(), new StockInformationPage(driver).getHistoricalLast(), 0.01);
    }

}
