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
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;

import static org.testng.Assert.fail;


public class CheckPublicSite extends AbstractSpec {
    private final String Q4WebVersionNumber = "4.3.0.61";

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckPreviewSite.java \\\\


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
      Second changes include making a .properties file including ALL the selectors. THEN making them all do the .property thing. DONE
     Last is switching this to testNG*/

    private static StockInformationPage stockInformationPage;
    private static HomePage homePage;
    private static FinancialReportsPage financialReportsPage;
    private static LivePressReleases livePressReleases;
    private static LiveEvents liveEvents;
    private static LivePresentations livePresentations;
    private static SECFilingsPage secFilingsPage;
    private static RSSFeedsPage rssFeedsPage;
    private static BoardOfDirectorsPage boardOfDirectorsPage;


    @BeforeTest
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        //driver.get("http://fiesta.q4web.newtest/stock-information/default.aspx");

        stockInformationPage = new StockInformationPage(driver);
        homePage = new HomePage(driver);
        financialReportsPage = new FinancialReportsPage(driver);
        livePressReleases = new LivePressReleases(driver);
        liveEvents = new LiveEvents(driver);
        livePresentations = new LivePresentations(driver);
        secFilingsPage = new SECFilingsPage(driver);
        rssFeedsPage = new RSSFeedsPage(driver);
        boardOfDirectorsPage = new BoardOfDirectorsPage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");

    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals(homePage.getVersionNumber(), Q4WebVersionNumber
                , "Displayed version number is incorrect");
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
                , 0.1, "Stock 52 week low isn't accurate");
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
    public void historicalQuotesWork(){
        // going to Stock Information page and checking that historical quotes module appears
        try{
           Assert.assertTrue(homePage.selectStockInformationFromMenu().historicalQuotesAreDisplayed()
                   , "Historical quotes are not displayed.");
        }catch (TimeoutException e) {
           driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        // checking that all stock quote values are present and doing basic validation (like checking that stock prices are above zero)
        try {
           Assert.assertTrue(stockInformationPage.historicalQuoteValuesArePresent()
                   , "One or more historical quote values are missing or invalid.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        //
        try {
            String[] lastDayQuotes = stockInformationPage.getHistoricalQuote(); // saving values currently displayed
            stockInformationPage.changeQuoteDate(); // changing to an earlier date

            // revalidating the new values
            Assert.assertTrue(stockInformationPage.historicalQuoteValuesArePresent()
                    ,"One or more historical quote values are missing or invalid after changing date.");

            // checking that all the values have changed
            String[] olderDayQuotes = stockInformationPage.getHistoricalQuote();
            for (int i = 0; i < olderDayQuotes.length; i++) {
                Assert.assertFalse(olderDayQuotes[i].equals(lastDayQuotes[i])
                        , "Identical value (" + lastDayQuotes[i] + " = " + olderDayQuotes[i] +
                       ") present in last trading day's historical quote and older (May 25th, 2016) historical quote.");
                }
        }catch (TimeoutException e){
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

    @Test
    public void financialReportsWork(){
        // going to Financial Reports page and checking that at least one report is displayed
        try {
            Assert.assertTrue(homePage.selectFinancialReportsFromMenu().financialReportsAreDisplayed()
                    , "Financial reports are not displayed.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all report titles are valid links
        Assert.assertTrue(financialReportsPage.reportTitlesAreLinks()
                , "One or more financial report titles are not links.");
        // checking that at least one report title links to a .pdf file
        Assert.assertTrue(financialReportsPage.pdfLinkIsPresent()
                , "No financial reports links to a .pdf file.");
    }

    @Test
    public void pressReleasesWork(){
       // going to Press Releases page and checking that at least one press release is displayed
        try {
            Assert.assertTrue(homePage.selectPressReleasesFromMenu().pressReleasesAreDisplayed()
                   , "Press releases are not displayed.");
        }catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all press releases displayed are from the current year
        Assert.assertTrue(livePressReleases.pressReleasesAreAllFromYear(Year.now().toString())
                , "One or more displayed press releases are not from the current year.");
        // switching year to 2015 and checking that all press releases displayed are from 2015
        livePressReleases.switchYearTo("2015");
        Assert.assertTrue(livePressReleases.pressReleasesAreAllFromYear("2015")
                , "One or more displayed press releases are not from the selected year (2015).");
        // clicking the first headline and checking the the opened press release has a download link
        try {
            livePressReleases.openFirstPressRelease();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue(livePressReleases.pressReleaseIsOpen()
                , "Press release is not open.");
    }

    @Test
    public void eventsWork(){
        // going to Events page and checking that at least one event is displayed
        try {
            Assert.assertTrue(homePage.selectEventsFromMenu().eventsAreDisplayed()
                    ,"Upcoming events are not displayed.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all displayed events take place today or in the future
        Assert.assertTrue(liveEvents.allEventsAreUpcoming()
                , "One or more events displayed are not upcoming.");
        // clicking Past Events button and checking that at least one event is displayed and all displayed events are in the past
        liveEvents.switchToPastEvents();
        Assert.assertTrue(liveEvents.eventsAreDisplayed(), "Past events are not displayed.");
        try {
            Assert.assertTrue(liveEvents.allEventsArePast(), "One or more events displayed are not past.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // opening the first event and checking that the Events Details module is displayed
        try{
              liveEvents.openFirstEvent();
        }catch (TimeoutException e){
             driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        try{
            Assert.assertTrue(liveEvents.eventIsOpen(), "Event details have not been loaded.");
        }catch (TimeoutException e)
        {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void presentationsWork(){
        // going to Presentations page and checking that at least one presentation is displayed
        try{
            Assert.assertTrue(homePage.selectPresentationsFromMenu().presentationsAreDisplayed()
                    , "Presentations are not displayed.");
        } catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all presentations displayed are from the current year
        Assert.assertTrue(livePresentations.presentationsAreAllFromYear(Year.now().toString())
                , "One or more displayed presentations are not from the current year.");
        // switching year to 2015 and checking that all presentations displayed are from 2015
        livePresentations.switchYearTo("2015");
        Assert.assertTrue(livePresentations.presentationsAreAllFromYear("2015")
                , "One or more displayed presentations are not from the selected year (2015).");
        // checking that all presentation links are valid links
        Assert.assertTrue(livePresentations.presentationLinksAreLinks()
                , "One or more presentation links are not links.");
        // checking that at least one link links to a .pdf file
        Assert.assertTrue(livePresentations.pdfLinkIsPresent()
                ,"No presentations link to a .pdf file.");
    }

    @Test
    public void secFilingsWork(){
        // going to SEC Filings page and checking that at least one filing is displayed
        try {
            Assert.assertTrue(homePage.selectSECFilingsFromMenu().filingsAreDisplayed()
                    ,"SEC filings are not displayed.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that all filings displayed are from the current year
        Assert.assertTrue(secFilingsPage.filingsAreAllFromYear(Year.now().toString())
                , "One or more displayed filings are not from the current year.");
        // switching year to 2015 and checking that all filings displayed are from 2015
        secFilingsPage.switchYearTo("2015");
        Assert.assertTrue(secFilingsPage.filingsAreAllFromYear("2015")
                , "One or more displayed filings are not from the selected year (2015).");
        // checking that all the PDF icons link to a .pdf file
        Assert.assertTrue(secFilingsPage.pdfIconsLinkToPDF()
                , "One or more pdf icons do not link to a .pdf file.");
    }

    @Test
    public void peopleWork(){
        // going to Board of Directors page and checking that at least one person is displayed
        try {
            Assert.assertTrue(homePage.selectBoardOfDirectorsFromMenu().peopleAreDisplayed()
                    , "People are not displayed." );
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        // checking that biographical information is displayed for every person
        Assert.assertTrue(boardOfDirectorsPage.peopleHaveBiographicalInformation()
                , "One or more people do not have biographical information.");
    }


    @Test
    public void rssPressReleaseWorks() {
        try{
            Assert.assertTrue(homePage.selectRSSFeedsFromMenu().rssFeedsExist()
                    , "RSS Feeds are not displayed.");
        }catch(TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue(rssFeedsPage.pressReleaseRSSExists()
                , "RSS Feeds for press release do not open correctly");
    }

    @Test
    public void rssEventsWorks(){
        Assert.assertTrue(homePage.selectRSSFeedsFromMenu().rssFeedsExist()
                , "RSS Feeds are not displayed.");
        Assert.assertTrue(rssFeedsPage.eventRSSExists(), "RSS Feeds for events do not open correctly" );
    }


    @Test 
    public void rssPresentationsWorks(){
        try{
            Assert.assertTrue(homePage.selectRSSFeedsFromMenu().rssFeedsExist()
                    , "RSS Feeds are not displayed.");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue(rssFeedsPage.presentationRSSExists()
                , "RSS Feeds for presentations do not open correctly");
    }

    @Test
    public void emailAlertsWork() {
        EmailAlertsPage emailAlertsPage = homePage.selectEmailAlertsFromMenu();
        String wrongEmail = "QWEASDZXC1234567";
        String rightEmail = "kelvint@q4inc.com";
        boolean buttonsActivated = true; //State of the buttons

        Assert.assertTrue(emailAlertsPage.clickAllButtonsWorks(buttonsActivated)
                , "Buttons did not behave as expected" );

        buttonsActivated = false;

        Assert.assertTrue(emailAlertsPage.clickAllButtonsWorks(buttonsActivated)
                , "Buttons did not behave as expected");
        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Selecting no options for the mailing list still allowed submitting");

        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Entering no credentials allowed submitting");
        emailAlertsPage.enterSubEmailAddress(wrongEmail);
        Assert.assertFalse(emailAlertsPage.clickSubmitWorks()
                , "Entering an incorrectly formatted password works");
        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterSubEmailAddress(rightEmail);
        try{
            Assert.assertTrue(emailAlertsPage.clickSubmitWorks(), "Submitting doesn't work");
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test
    public void unsubscribeEmailAlertsWorks() { //Timeouts still occur, despite all the escapes :/
        EmailAlertsPage emailAlertsPage = homePage.selectEmailAlertsFromMenu();
        String incorrectFormEmail = "QWEASDZXC1234567";
        String wrongEmail = "telvink@q4inc.com"; //never used to subscribe
        String rightEmail = "kelvint@q4inc.com";

        Assert.assertFalse(emailAlertsPage.clickUnsubscribeWorks()
                , "Entering no credentials allowed submitting");

        emailAlertsPage.enterUnsubEmailAddress(incorrectFormEmail);
        Assert.assertFalse(emailAlertsPage.clickUnsubscribeWorks()
                , "Unsubbing with an incorrectly formatted email works");

        emailAlertsPage.clearAllTextFields();
        emailAlertsPage.enterUnsubEmailAddress(wrongEmail);

        Assert.assertFalse(emailAlertsPage.clickUnsubscribeWorks()
                , "Unsubbing with a non-subscribed email works");
        emailAlertsPage.clearAllTextFields();

        emailAlertsPage.enterUnsubEmailAddress(rightEmail);

        try{
            Assert.assertTrue(emailAlertsPage.clickUnsubscribeWorks(), "Unsubscribing doesn't work");
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
        // going to Investment Calculator page and checking that the investment calculator is displayed
        InvestmentCalculatorPage investmentCalculatorPage = homePage.selectInvestmentCalculatorFromMenu();
        Assert.assertTrue(investmentCalculatorPage.investmentCalculatorExists()
                , "Investment Calculator is not displayed.");
        // performing calculation of $100 investment from startDate to endDate and checking that the growth chart is displayed
        Assert.assertTrue(investmentCalculatorPage.performSampleCalculation(startDate, endDate).growthChartIsPresent()
                , "Growth chart is not displayed after clicking calculate.");
        // checking that the table header has the expected symbol
        Assert.assertTrue(investmentCalculatorPage.resultsIncludeSymbol(symbol)
                , "Expected symbol is not present." );
        // checking whether hovering over the chart produces hovertext
        Assert.assertTrue(investmentCalculatorPage.canHoverOverGrowthChart()
                , "Cannot hover over the growth chart." );
        // checking whether values in table are present and properly formatted
        Assert.assertTrue(investmentCalculatorPage.growthDataIsValid(), "Invalid growth data is displayed." );
        // checking whether the displayed starting and ending dates are correct
        Assert.assertEquals(investmentCalculatorPage.getStartDate(), startDate, "Start date does not match" );
        Assert.assertEquals(investmentCalculatorPage.getEndDate(), endDate, "End date does not match");
        // clicking the close button and checking that results are no longer displayed
        Assert.assertFalse(investmentCalculatorPage.closeResults().resultsAreOpen()
                , "Results area is not closed.");
        // adding comparedSymbol to compare with, recalculating, and checking that the growth chart is displayed
        Assert.assertTrue(investmentCalculatorPage.recalculateAndCompareTo(comparedSymbol).growthChartIsPresent()
                , "Recalculation with compare to other symbol doesn't work." );
        // checking that the table header includes both the original and the compared to symbols
        Assert.assertTrue(investmentCalculatorPage.resultsIncludeSymbol(symbol)
                , "Expected symbol is not present after recalculating." );
        Assert.assertTrue(investmentCalculatorPage.resultsIncludeSymbol(comparedSymbol)
                , "Compared to symbol is not present." );
    }

    @Test
    public void faqPageWorks(){
        // going to FAQ page and checking that at least one question is displayed at the top of the page
        FAQPage faqPage = homePage.selectFAQFromMenu();
        int numQuestionsTop = faqPage.getNumQuestionsTop();
        Assert.assertTrue(numQuestionsTop > 0, "No questions are displayed at top of page." );
        // checking that the same number of questions are displayed at the top of the page as are displayed below
        int numQuestionsBelow = faqPage.getNumQuestionsBelow();
        Assert.assertEquals(numQuestionsBelow, numQuestionsTop
     ,"Number of questions displayed at below is different from number of questions displayed at top of page");
        // checking that there is an answer for every question
        int numAnswers = faqPage.getNumAnswers();
        Assert.assertEquals(numAnswers, numQuestionsBelow, "There is not an answer for every question");
        // clicking on the first question (on the top of the page) and checking that the page scrolls down to that question below
        // this may not work if the vertical distance between the first question below and the bottom of the page is less than the window height
        Assert.assertEquals(faqPage.clickFirstQuestion().getScrollPositionY(), faqPage.getFirstQuestionY()
                , "Page does not scroll down after clicking question");
        // clicking on "back to top" and checking that the page scrolls back up to the top
        Assert.assertEquals(faqPage.clickBackToTop().getScrollPositionY(), 0
                , "Page does not scroll back up after clicking 'back to top'");
    }




}
