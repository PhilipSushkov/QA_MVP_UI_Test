package specs.PublicSite;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.fail;

public class CheckPublicSite extends AbstractSpec {
    private final String Q4WebVersionNumber = "4.3.0.56";

    @Before
    public void goToPublicSite() {
        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");
        Assert.assertTrue("Home page of public site has not been loaded.", new HomePage(driver).logoIsPresent());
    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals("Displayed version number is incorrect", Q4WebVersionNumber,
                new HomePage(driver).getVersionNumber());
    }

    @Test
    public void stockChartXigniteWorks(){
        Assert.assertTrue("Xignite stock chart is not displayed."
                , new HomePage(driver).selectStockInformationFromMenu().stockChartXigniteIsDisplayed());
        new StockInformationPage(driver).switchChartXigniteTo1Month();
        int xStart1Month = new StockInformationPage(driver).getChartXigniteSliderXStart();
        new StockInformationPage(driver).switchChartXigniteTo1Quarter();
        int xStart1Quarter = new StockInformationPage(driver).getChartXigniteSliderXStart();
        new StockInformationPage(driver).switchChartXigniteTo1Year();
        int xStart1Year = new StockInformationPage(driver).getChartXigniteSliderXStart();
        Assert.assertTrue("Xignite stock chart period is not switching properly.\nxStart1Year="+xStart1Year+
                        "\nxStart1Quarter="+xStart1Quarter+"\nxStart1Month="+xStart1Month,
                xStart1Month>xStart1Quarter && xStart1Quarter>xStart1Year);
        Assert.assertTrue("Hovering over Xignite chart doesn't work.",
                new StockInformationPage(driver).canHoverOverChartXignite());
    }

    @Test
    public void stockQuoteWorks(){
        Assert.assertTrue("Stock quote is not displayed.",
                new HomePage(driver).selectStockInformationFromMenu().stockQuoteIsDisplayed());
        Assert.assertTrue("One or more stock quote values are missing or invalid.",
                new StockInformationPage(driver).stockQuoteValuesArePresent());
    }

    @Test
    public void stockQuoteValuesAreAccurate() throws TimeoutException{

        Assert.assertTrue("Stock quote is not displayed.",
                new HomePage(driver).selectStockInformationFromMenu().stockQuoteIsDisplayed());

        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);

        StockQuote stockQuote;
        try {
            stockQuote = YahooFinance.get("TXRH").getQuote(false);
        }catch (IOException e){
            fail("Problem retrieving stock data from Yahoo Finance.");
            return;
        }
        Assert.assertEquals("Stock price isn't accurate", stockQuote.getPrice().doubleValue(),
                new StockInformationPage(driver).getStockPrice(), 0.25);
        Assert.assertEquals("Stock change isn't accurate", stockQuote.getChange().doubleValue()
                , new StockInformationPage(driver).getStockChange(), 0.25);
        Assert.assertEquals("Stock % change isn't accurate", stockQuote.getChangeInPercent().doubleValue()
                , new StockInformationPage(driver).getStockPChange(), 1);
        Assert.assertEquals("Stock intraday high isn't accurate", stockQuote.getDayHigh().doubleValue()
                , new StockInformationPage(driver).getStockDayHigh(), 0.25);
        Assert.assertEquals("Stock 52 week high isn't accurate", stockQuote.getYearHigh().doubleValue()
                , new StockInformationPage(driver).getStock52WeekHigh(), 0.1);
        Assert.assertEquals("Stock intraday low isn't accurate", stockQuote.getDayLow().doubleValue()
                , new StockInformationPage(driver).getStockDayLow(), 0.25);
        Assert.assertEquals("Stock 52 week low isn't accurate", stockQuote.getYearLow().doubleValue()
                , new StockInformationPage(driver).getStock52WeekLow(), 0.1);
        Assert.assertEquals("Stock today's open isn't accurate", stockQuote.getOpen().doubleValue()
                , new StockInformationPage(driver).getStockTodayOpen(), 0.01);
        Assert.assertEquals("Stock previous close isn't accurate", stockQuote.getPreviousClose().doubleValue()
                , new StockInformationPage(driver).getStockPreviousClose(), 0.01);
    }

    @Test
    public void stockChartTickertechWorks() {

        try {
            //go to stock information page and check that chart is present
            Assert.assertTrue("Tickertech stock chart is not displayed.", new HomePage(driver).selectStockInformationFromMenu().stockChartTickertechIsDisplayed());
            //cycle through time periods and check that chart changes accordingly
            new StockInformationPage(driver).switchChartTickertechTo1Month();
            Assert.assertThat("Tickertech chart isn't displaying 1 month period", new StockInformationPage(driver).getTickertechSRC(), containsString("period=1m&"));
            new StockInformationPage(driver).switchChartTickertechTo1Quarter();
            Assert.assertThat("Tickertech chart isn't displaying 1 quarter period", new StockInformationPage(driver).getTickertechSRC(), containsString("period=1q&"));
            new StockInformationPage(driver).switchChartTickertechTo1Year();
            Assert.assertThat("Tickertech chart isn't displaying 1 year period", new StockInformationPage(driver).getTickertechSRC(), containsString("period=1y&"));
            //try switching to % change and check that chart changes accordingly
            new StockInformationPage(driver).switchChartTickertechToPChange();
            Assert.assertThat("Tickertech chart isn't displaying % change", new StockInformationPage(driver).getTickertechSRC(), containsString("period=1yp&"));
            //try switching back to price and check that chart changes accordingly
            new StockInformationPage(driver).switchChartTickertechToPrice();
            Assert.assertThat("Tickertech chart isn't displaying price", new StockInformationPage(driver).getTickertechSRC(), containsString("period=1y&"));
            //cycle though chart types and check that chart changes accordingly
            new StockInformationPage(driver).switchChartTickertechToFillToPrevClose();
            Assert.assertThat("Tickertech chart isn't displaying fill to prev. close type", new StockInformationPage(driver).getTickertechSRC(), containsString("fillBelowLine=prev&"));
            new StockInformationPage(driver).switchChartTickertechToLine();
            Assert.assertThat("Tickertech chart isn't displaying line type", new StockInformationPage(driver).getTickertechSRC(), containsString("fillBelowLine=no&"));
            Assert.assertThat("Tickertech chart isn't displaying line type", new StockInformationPage(driver).getTickertechSRC(), containsString("type=line&"));
            Assert.assertThat("Tickertech chart isn't displaying line type", new StockInformationPage(driver).getTickertechSRC(), containsString("showHighsLows=no&"));
            new StockInformationPage(driver).switchChartTickertechToPoint();
            Assert.assertThat("Tickertech chart isn't displaying point type", new StockInformationPage(driver).getTickertechSRC(), containsString("type=point&"));
            Assert.assertThat("Tickertech chart isn't displaying point type", new StockInformationPage(driver).getTickertechSRC(), containsString("showHighsLows=yes&"));
            new StockInformationPage(driver).switchChartTickertechToBar();
            Assert.assertThat("Tickertech chart isn't displaying bar type", new StockInformationPage(driver).getTickertechSRC(), containsString("type=bar&"));
            Assert.assertThat("Tickertech chart isn't displaying bar type", new StockInformationPage(driver).getTickertechSRC(), containsString("showHighsLows=no&"));
            new StockInformationPage(driver).switchChartTickertechToCandleStick();
            Assert.assertThat("Tickertech chart isn't displaying candle stick type", new StockInformationPage(driver).getTickertechSRC(), containsString("type=candle&"));
            new StockInformationPage(driver).switchChartTickertechToMountain();
            Assert.assertThat("Tickertech chart isn't displaying mountain type", new StockInformationPage(driver).getTickertechSRC(), containsString("fillBelowLine=yes&"));
            Assert.assertThat("Tickertech chart isn't displaying mountain type", new StockInformationPage(driver).getTickertechSRC(), containsString("type=line&"));
            //select one or more indices/stocks and check that chart changes accordingly
            new StockInformationPage(driver).tickertechCompareVs("", true, false, true, false); // comparing with Dow and S&P
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", new StockInformationPage(driver).getTickertechSRC(), containsString("showVolumeTable=no&"));
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", new StockInformationPage(driver).getTickertechSRC(), containsString("showLegendTable=yes&"));
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", new StockInformationPage(driver).getTickertechSRC(), containsString("period=1yp&"));
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", new StockInformationPage(driver).getTickertechSRC(), containsString("fillBelowLine=no&"));
            Assert.assertThat("Tickertech chart isn't displaying Dow and S&P only", new StockInformationPage(driver).getTickertechSRC(), containsString("symbols=TXRH,$DJI,SPX&"));
            new StockInformationPage(driver).tickertechCompareVs("", true, true, true, true); // comparing with all four indices
            Assert.assertThat("Tickertech chart isn't displaying all four indices", new StockInformationPage(driver).getTickertechSRC(), containsString("symbols=TXRH,$DJI,COMP,SPX,RUT&"));
            new StockInformationPage(driver).tickertechCompareVs("", false, true, false, false); // comparing with Nasdaq
            Assert.assertThat("Tickertech chart isn't displaying Nasdaq only", new StockInformationPage(driver).getTickertechSRC(), containsString("symbols=TXRH,COMP&"));
            new StockInformationPage(driver).tickertechCompareVs("AAPL", false, true, false, false); // comparing with AAPL and Nasdaq
            Assert.assertThat("Tickertech chart isn't displaying AAPL and Nasdaq only", new StockInformationPage(driver).getTickertechSRC(), containsString("symbols=TXRH,AAPL,COMP&"));

        }catch (TimeoutException e)
        {
            e.printStackTrace();
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

    }

    @Test
    public void historicalQuotesWork(){
        try{
           Assert.assertTrue("Historical quotes are not displayed.",
                   new HomePage(driver).selectStockInformationFromMenu().historicalQuotesAreDisplayed());
        }catch (TimeoutException e) {
           driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        try {
           Assert.assertTrue("One or more historical quote values are missing or invalid.",
                   new StockInformationPage(driver).historicalQuoteValuesArePresent());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        try {
            String[] lastDayQuotes = new StockInformationPage(driver).getHistoricalQuote();
            new StockInformationPage(driver).changeQuoteDate();

            Assert.assertTrue("One or more historical quote values are missing or invalid after changing date.",
                    new StockInformationPage(driver).historicalQuoteValuesArePresent());

            String[] olderDayQuotes = new StockInformationPage(driver).getHistoricalQuote();
            for (int i = 0; i < olderDayQuotes.length; i++) {
                Assert.assertFalse("Identical value (" + lastDayQuotes[i] + " = " + olderDayQuotes[i] + ") present in last trading day's historical quote and older (May 25th, 2016) historical quote.",
                olderDayQuotes[i].equals(lastDayQuotes[i]));
                }
        }catch (TimeoutException e){
            e.printStackTrace();
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void historicalQuoteValuesAreAccurate() {
        try {
            Assert.assertTrue("Historical quotes are not displayed.", new HomePage(driver).selectStockInformationFromMenu().historicalQuotesAreDisplayed());

            // checking historical values from last trading day
            Calendar lastTradingDay = new StockInformationPage(driver).getCurrentDate();
            HistoricalQuote lastTradingDayQuotes;
            try {
                lastTradingDayQuotes = YahooFinance.get("TXRH").getHistory(lastTradingDay, lastTradingDay, Interval.DAILY).get(0);
            } catch (IOException e) {
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
            } catch (IOException e) {
                fail("Problem retrieving older day stock data from Yahoo Finance.");
                return;
            }
            Assert.assertEquals("Older day high isn't accurate", olderDayQuotes.getHigh().doubleValue()
                    , new StockInformationPage(driver).getHistoricalHigh(), 0.01);
            Assert.assertEquals("Older day low isn't accurate", olderDayQuotes.getLow().doubleValue()
                    , new StockInformationPage(driver).getHistoricalLow(), 0.01);
            Assert.assertEquals("Older day volume isn't accurate", olderDayQuotes.getVolume().doubleValue()
                    , new StockInformationPage(driver).getHistoricalVolume(), 5000);
            Assert.assertEquals("Older day opening price isn't accurate",olderDayQuotes.getOpen().doubleValue()
                    , new StockInformationPage(driver).getHistoricalOpen(), 0.01);
            Assert.assertEquals("Older day last price isn't accurate", olderDayQuotes.getClose().doubleValue()
                    , new StockInformationPage(driver).getHistoricalLast(), 0.01);

        }catch (TimeoutException e)
        {
            e.printStackTrace();
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void financialReportsWork(){
        try {
            Assert.assertTrue("Financial reports are not displayed."
                    , new HomePage(driver).selectFinancialReportsFromMenu().financialReportsAreDisplayed());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("One or more financial report titles are not links."
                , new FinancialReportsPage(driver).reportTitlesAreLinks());
        Assert.assertTrue("No financial reports links to a .pdf file."
                , new FinancialReportsPage(driver).pdfLinkIsPresent());
    }

    @Test
    public void pressReleasesWork(){
       try {
           Assert.assertTrue("Press releases are not displayed."
                   , new HomePage(driver).selectPressReleasesFromMenu().pressReleasesAreDisplayed());
       }catch (TimeoutException e) {
           driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
       }
        Assert.assertTrue("One or more displayed press releases are not from the current year."
                , new LivePressReleases(driver).pressReleasesAreAllFromYear(Year.now().toString()));
        new LivePressReleases(driver).switchYearTo("2015");
        Assert.assertTrue("One or more displayed press releases are not from the selected year (2015)."
                , new LivePressReleases(driver).pressReleasesAreAllFromYear("2015"));
        try {
            new LivePressReleases(driver).openFirstPressRelease();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("Press release is not open."
                , new LivePressReleases(driver).pressReleaseIsOpen());
    }

    @Test
    public void eventsWork(){
        try {
            Assert.assertTrue("Upcoming events are not displayed."
                    , new HomePage(driver).selectEventsFromMenu().eventsAreDisplayed());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("One or more events displayed are not upcoming."
                , new LiveEvents(driver).allEventsAreUpcoming());
        new LiveEvents(driver).switchToPastEvents();
        Assert.assertTrue("Past events are not displayed.", new LiveEvents(driver).eventsAreDisplayed());
        try {
            Assert.assertTrue("One or more events displayed are not past.", new LiveEvents(driver).allEventsArePast());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        try{
              new LiveEvents(driver).openFirstEvent();
        }catch (TimeoutException e){
             driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        try{
            Assert.assertTrue("Event details have not been loaded.", new LiveEvents(driver).eventIsOpen());
        }catch (TimeoutException e)
        {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void presentationsWork(){
        try{
            Assert.assertTrue("Presentations are not displayed."
                    , new HomePage(driver).selectPresentationsFromMenu().presentationsAreDisplayed());
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("One or more displayed presentations are not from the current year."
                , new LivePresentations(driver).presentationsAreAllFromYear(Year.now().toString()));
        new LivePresentations(driver).switchYearTo("2015");
        Assert.assertTrue("One or more displayed presentations are not from the selected year (2015)."
                , new LivePresentations(driver).presentationsAreAllFromYear("2015"));
        Assert.assertTrue("One or more presentation links are not links."
                , new LivePresentations(driver).presentationLinksAreLinks());
        Assert.assertTrue("No presentations link to a .pdf file."
                , new LivePresentations(driver).pdfLinkIsPresent());
    }

    @Test
    public void secFilingsWork(){
        try {
            Assert.assertTrue("SEC filings are not displayed."
                    , new HomePage(driver).selectSECFilingsFromMenu().filingsAreDisplayed());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("One or more displayed filings are not from the current year."
                , new SECFilingsPage(driver).filingsAreAllFromYear(Year.now().toString()));
        new SECFilingsPage(driver).switchYearTo("2015");
        Assert.assertTrue("One or more displayed filings are not from the selected year (2015)."
                , new SECFilingsPage(driver).filingsAreAllFromYear("2015"));
        Assert.assertTrue("One or more pdf icons do not link to a .pdf file."
                , new SECFilingsPage(driver).pdfIconsLinkToPDF());
    }

    @Test
    public void peopleWork(){
        try {
            Assert.assertTrue("People are not displayed."
                    , new HomePage(driver).selectBoardOfDirectorsFromMenu().peopleAreDisplayed());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("One or more people do not have biographical information."
                , new BoardOfDirectorsPage(driver).peopleHaveBiographicalInformation());
    }


    @Test
    public void rssPressReleaseWorks() {
        try{
            Assert.assertTrue("RSS Feeds are not displayed."
                    , new HomePage(driver).selectRSSFeedsFromMenu().rssFeedsExist());
        }catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("RSS Feeds for press release do not open correctly"
                , new RSSFeedsPage(driver).pressReleaseRSSExists());
    }

    @Test
    public void rssEventsWorks(){
        Assert.assertTrue("RSS Feeds are not displayed."
                , new HomePage(driver).selectRSSFeedsFromMenu().rssFeedsExist());
        Assert.assertTrue("RSS Feeds for events do not open correctly", new RSSFeedsPage(driver).eventRSSExists());
    }


    @Test 
    public void rssPresentationsWorks(){
        try{
            Assert.assertTrue("RSS Feeds are not displayed."
                    , new HomePage(driver).selectRSSFeedsFromMenu().rssFeedsExist());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("RSS Feeds for presentations do not open correctly"
                , new RSSFeedsPage(driver).presentationRSSExists());
    }

    @Test
    public void emailAlertsWork() {
        EmailAlertsPage emailAlertsPage = new HomePage(driver).selectEmailAlertsFromPage();
        String wrongEmail = "QWEASDZXC1234567";
        String rightEmail = "kelvint@q4inc.com";
        boolean buttonsActivated = true; //State of the buttons

        Assert.assertTrue("Buttons did not behave as expected", emailAlertsPage.clickAllButtonsWorks(buttonsActivated));

        buttonsActivated = false;

        Assert.assertTrue("Buttons did not behave as expected", emailAlertsPage.clickAllButtonsWorks(buttonsActivated));
        Assert.assertFalse("Selecting no options for the mailing list still allowed submitting"
                , emailAlertsPage.clickSubmitWorks());

        Assert.assertFalse("Entering no credentials allowed submitting"
                , emailAlertsPage.clickSubmitWorks());
        emailAlertsPage.enterSubEmailAddress(wrongEmail);
        Assert.assertFalse("Entering an incorrectly formatted password works"
                , emailAlertsPage.clickSubmitWorks());
        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterSubEmailAddress(rightEmail);
        try{
            Assert.assertTrue("Submitting doesn't work", emailAlertsPage.clickSubmitWorks());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void unsubscribeEmailAlertsWorks() { //Timeouts still occur, despite all the escapes :/
        EmailAlertsPage emailAlertsPage = new HomePage(driver).selectEmailAlertsFromPage();
        String incorrectFormEmail = "QWEASDZXC1234567";
        String wrongEmail = "telvink@q4inc.com"; //never used to subscribe
        String rightEmail = "kelvint@q4inc.com";

        Assert.assertFalse("Entering no credentials allowed submitting"
                , emailAlertsPage.clickUnsubscribeWorks());

        emailAlertsPage.enterUnsubEmailAddress(incorrectFormEmail);
        Assert.assertFalse("Unsubbing with an incorrectly formatted email works"
                , emailAlertsPage.clickUnsubscribeWorks());

        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterUnsubEmailAddress(wrongEmail);

        Assert.assertFalse("Unsubbing with a non-subscribed email works"
                , emailAlertsPage.clickUnsubscribeWorks());
        emailAlertsPage.clearAllTextFields();

        emailAlertsPage.enterUnsubEmailAddress(rightEmail);

        try{
            Assert.assertTrue("Unsubscribing doesn't work", emailAlertsPage.clickUnsubscribeWorks());
        }catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void investmentCalculatorWorks(){
        String symbol = "NWL"; // This is the symbol of the stock used by the module
        String comparedSymbol = "AAPL";
        LocalDate startDate = LocalDate.of(2016, Month.APRIL, 4);
        LocalDate endDate = LocalDate.of(2016, Month.OCTOBER, 28);
        InvestmentCalculatorPage investmentCalculatorPage = new HomePage(driver).selectInvestmentCalculatorFromMenu();
        Assert.assertTrue("Investment Calculator is not displayed.", investmentCalculatorPage.investmentCalculatorExists());
        Assert.assertTrue("Growth chart is not displayed after clicking calculate.", investmentCalculatorPage.performSampleCalculation(startDate, endDate).growthChartIsPresent());
        Assert.assertTrue("Expected symbol is not present.", investmentCalculatorPage.resultsIncludeSymbol(symbol));
        Assert.assertTrue("Cannot hover over the growth chart.", investmentCalculatorPage.canHoverOverGrowthChart());
        Assert.assertTrue("Invalid growth data is displayed.", investmentCalculatorPage.growthDataIsValid());
        Assert.assertEquals("Start date does not match", startDate, investmentCalculatorPage.getStartDate());
        Assert.assertEquals("End date does not match", endDate, investmentCalculatorPage.getEndDate());
        Assert.assertFalse("Results area is not closed.", investmentCalculatorPage.closeResults().resultsAreOpen());
        Assert.assertTrue("Recalculation with compare to other symbol doesn't work.", investmentCalculatorPage.recalculateAndCompareTo(comparedSymbol).growthChartIsPresent());
        Assert.assertTrue("Expected symbol is not present after recalculating.", investmentCalculatorPage.resultsIncludeSymbol(symbol));
        Assert.assertTrue("Compared to symbol is not present.", investmentCalculatorPage.resultsIncludeSymbol(comparedSymbol));
    }

}
