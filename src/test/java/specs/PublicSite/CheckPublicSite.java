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


    /** Changes necessary to make include removing all the "new XXX(drivers).YYY with a declaration, instead, in the @Before
     and replacing it in all pieces of code. DONE
      Second changes include making a .properties file including ALL the selectors
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


    @Before
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

        Assert.assertTrue("Home page of public site has not been loaded.", homePage.logoIsPresent());


    }

    @Test
    public void versionNumberIsCorrect(){
        Assert.assertEquals("Displayed version number is incorrect", Q4WebVersionNumber,
                homePage.getVersionNumber());
    }

    @Test
    public void stockChartXigniteWorks(){
        Assert.assertTrue("Xignite stock chart is not displayed."
                , new HomePage(driver).selectStockInformationFromMenu().stockChartXigniteIsDisplayed());
        stockInformationPage.switchChartXigniteTo1Month();
        int xStart1Month = stockInformationPage.getChartXigniteSliderXStart();
        stockInformationPage.switchChartXigniteTo1Quarter();
        int xStart1Quarter = stockInformationPage.getChartXigniteSliderXStart();
        stockInformationPage.switchChartXigniteTo1Year();
        int xStart1Year = stockInformationPage.getChartXigniteSliderXStart();
        Assert.assertTrue("Xignite stock chart period is not switching properly.\nxStart1Year="+xStart1Year+
                        "\nxStart1Quarter="+xStart1Quarter+"\nxStart1Month="+xStart1Month,
                xStart1Month>xStart1Quarter && xStart1Quarter>xStart1Year);
        Assert.assertTrue("Hovering over Xignite chart doesn't work.",
                stockInformationPage.canHoverOverChartXignite());
    }

    @Test
    public void stockQuoteWorks(){
        Assert.assertTrue("Stock quote is not displayed.",
                new HomePage(driver).selectStockInformationFromMenu().stockQuoteIsDisplayed());
        Assert.assertTrue("One or more stock quote values are missing or invalid.",
                stockInformationPage.stockQuoteValuesArePresent());
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
                stockInformationPage.getStockPrice(), 0.25);
        Assert.assertEquals("Stock change isn't accurate", stockQuote.getChange().doubleValue()
                , stockInformationPage.getStockChange(), 0.25);
        Assert.assertEquals("Stock % change isn't accurate", stockQuote.getChangeInPercent().doubleValue()
                , stockInformationPage.getStockPChange(), 1);
        Assert.assertEquals("Stock intraday high isn't accurate", stockQuote.getDayHigh().doubleValue()
                , stockInformationPage.getStockDayHigh(), 0.25);
        Assert.assertEquals("Stock 52 week high isn't accurate", stockQuote.getYearHigh().doubleValue()
                , stockInformationPage.getStock52WeekHigh(), 0.1);
        Assert.assertEquals("Stock intraday low isn't accurate", stockQuote.getDayLow().doubleValue()
                , stockInformationPage.getStockDayLow(), 0.25);
        Assert.assertEquals("Stock 52 week low isn't accurate", stockQuote.getYearLow().doubleValue()
                , stockInformationPage.getStock52WeekLow(), 0.1);
        Assert.assertEquals("Stock today's open isn't accurate", stockQuote.getOpen().doubleValue()
                , stockInformationPage.getStockTodayOpen(), 0.01);
        Assert.assertEquals("Stock previous close isn't accurate", stockQuote.getPreviousClose().doubleValue()
                , stockInformationPage.getStockPreviousClose(), 0.01);
    }

    @Test
    public void stockChartTickertechWorks() {

        try {
            //go to stock information page and check that chart is present
            Assert.assertTrue("Tickertech stock chart is not displayed.", new HomePage(driver).selectStockInformationFromMenu().stockChartTickertechIsDisplayed());
            //cycle through time periods and check that chart changes accordingly
            stockInformationPage.switchChartTickertechTo1Month();
            Assert.assertThat("Tickertech chart isn't displaying 1 month period", stockInformationPage.getTickertechSRC(), containsString("period=1m&"));
            stockInformationPage.switchChartTickertechTo1Quarter();
            Assert.assertThat("Tickertech chart isn't displaying 1 quarter period", stockInformationPage.getTickertechSRC(), containsString("period=1q&"));
            stockInformationPage.switchChartTickertechTo1Year();
            Assert.assertThat("Tickertech chart isn't displaying 1 year period", stockInformationPage.getTickertechSRC(), containsString("period=1y&"));
            //try switching to % change and check that chart changes accordingly
            stockInformationPage.switchChartTickertechToPChange();
            Assert.assertThat("Tickertech chart isn't displaying % change", stockInformationPage.getTickertechSRC(), containsString("period=1yp&"));
            //try switching back to price and check that chart changes accordingly
            stockInformationPage.switchChartTickertechToPrice();
            Assert.assertThat("Tickertech chart isn't displaying price", stockInformationPage.getTickertechSRC(), containsString("period=1y&"));
            //cycle though chart types and check that chart changes accordingly
            stockInformationPage.switchChartTickertechToFillToPrevClose();
            Assert.assertThat("Tickertech chart isn't displaying fill to prev. close type", stockInformationPage.getTickertechSRC(), containsString("fillBelowLine=prev&"));
            stockInformationPage.switchChartTickertechToLine();
            Assert.assertThat("Tickertech chart isn't displaying line type", stockInformationPage.getTickertechSRC(), containsString("fillBelowLine=no&"));
            Assert.assertThat("Tickertech chart isn't displaying line type", stockInformationPage.getTickertechSRC(), containsString("type=line&"));
            Assert.assertThat("Tickertech chart isn't displaying line type", stockInformationPage.getTickertechSRC(), containsString("showHighsLows=no&"));
            stockInformationPage.switchChartTickertechToPoint();
            Assert.assertThat("Tickertech chart isn't displaying point type", stockInformationPage.getTickertechSRC(), containsString("type=point&"));
            Assert.assertThat("Tickertech chart isn't displaying point type", stockInformationPage.getTickertechSRC(), containsString("showHighsLows=yes&"));
            stockInformationPage.switchChartTickertechToBar();
            Assert.assertThat("Tickertech chart isn't displaying bar type", stockInformationPage.getTickertechSRC(), containsString("type=bar&"));
            Assert.assertThat("Tickertech chart isn't displaying bar type", stockInformationPage.getTickertechSRC(), containsString("showHighsLows=no&"));
            stockInformationPage.switchChartTickertechToCandleStick();
            Assert.assertThat("Tickertech chart isn't displaying candle stick type", stockInformationPage.getTickertechSRC(), containsString("type=candle&"));
            stockInformationPage.switchChartTickertechToMountain();
            Assert.assertThat("Tickertech chart isn't displaying mountain type", stockInformationPage.getTickertechSRC(), containsString("fillBelowLine=yes&"));
            Assert.assertThat("Tickertech chart isn't displaying mountain type", stockInformationPage.getTickertechSRC(), containsString("type=line&"));
            //select one or more indices/stocks and check that chart changes accordingly
            stockInformationPage.tickertechCompareVs("", true, false, true, false); // comparing with Dow and S&P
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", stockInformationPage.getTickertechSRC(), containsString("showVolumeTable=no&"));
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", stockInformationPage.getTickertechSRC(), containsString("showLegendTable=yes&"));
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", stockInformationPage.getTickertechSRC(), containsString("period=1yp&"));
            Assert.assertThat("Tickertech chart isn't comparing with indices properly", stockInformationPage.getTickertechSRC(), containsString("fillBelowLine=no&"));
            Assert.assertThat("Tickertech chart isn't displaying Dow and S&P only", stockInformationPage.getTickertechSRC(), containsString("symbols=TXRH,$DJI,SPX&"));
            stockInformationPage.tickertechCompareVs("", true, true, true, true); // comparing with all four indices
            Assert.assertThat("Tickertech chart isn't displaying all four indices", stockInformationPage.getTickertechSRC(), containsString("symbols=TXRH,$DJI,COMP,SPX,RUT&"));
            stockInformationPage.tickertechCompareVs("", false, true, false, false); // comparing with Nasdaq
            Assert.assertThat("Tickertech chart isn't displaying Nasdaq only", stockInformationPage.getTickertechSRC(), containsString("symbols=TXRH,COMP&"));
            stockInformationPage.tickertechCompareVs("AAPL", false, true, false, false); // comparing with AAPL and Nasdaq
            Assert.assertThat("Tickertech chart isn't displaying AAPL and Nasdaq only", stockInformationPage.getTickertechSRC(), containsString("symbols=TXRH,AAPL,COMP&"));

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
                   stockInformationPage.historicalQuoteValuesArePresent());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        try {
            String[] lastDayQuotes = stockInformationPage.getHistoricalQuote();
            stockInformationPage.changeQuoteDate();

            Assert.assertTrue("One or more historical quote values are missing or invalid after changing date.",
                    stockInformationPage.historicalQuoteValuesArePresent());

            String[] olderDayQuotes = stockInformationPage.getHistoricalQuote();
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
            Calendar lastTradingDay = stockInformationPage.getCurrentDate();
            HistoricalQuote lastTradingDayQuotes;
            try {
                lastTradingDayQuotes = YahooFinance.get("TXRH").getHistory(lastTradingDay, lastTradingDay, Interval.DAILY).get(0);
            } catch (IOException e) {
                fail("Problem retrieving last trading day stock data from Yahoo Finance.");
                return;
            }
            Assert.assertEquals("Last trading day high isn't accurate", lastTradingDayQuotes.getHigh().doubleValue(), stockInformationPage.getHistoricalHigh(), 0.01);
            Assert.assertEquals("Last trading day low isn't accurate", lastTradingDayQuotes.getLow().doubleValue(), stockInformationPage.getHistoricalLow(), 0.01);
            Assert.assertEquals("Last trading day volume isn't accurate", lastTradingDayQuotes.getVolume().doubleValue(), stockInformationPage.getHistoricalVolume(), 5000);
            Assert.assertEquals("Last trading day opening price isn't accurate", lastTradingDayQuotes.getOpen().doubleValue(), stockInformationPage.getHistoricalOpen(), 0.01);
            Assert.assertEquals("Last trading day last price isn't accurate", lastTradingDayQuotes.getClose().doubleValue(), stockInformationPage.getHistoricalLast(), 0.01);

            // checking historical values from older day
            stockInformationPage.changeQuoteDate();
            Calendar olderDay = stockInformationPage.getCurrentDate();
            HistoricalQuote olderDayQuotes;
            try {
                olderDayQuotes = YahooFinance.get("TXRH").getHistory(olderDay, olderDay, Interval.DAILY).get(0);
            } catch (IOException e) {
                fail("Problem retrieving older day stock data from Yahoo Finance.");
                return;
            }
            Assert.assertEquals("Older day high isn't accurate", olderDayQuotes.getHigh().doubleValue()
                    , stockInformationPage.getHistoricalHigh(), 0.01);
            Assert.assertEquals("Older day low isn't accurate", olderDayQuotes.getLow().doubleValue()
                    , stockInformationPage.getHistoricalLow(), 0.01);
            Assert.assertEquals("Older day volume isn't accurate", olderDayQuotes.getVolume().doubleValue()
                    , stockInformationPage.getHistoricalVolume(), 5000);
            Assert.assertEquals("Older day opening price isn't accurate",olderDayQuotes.getOpen().doubleValue()
                    , stockInformationPage.getHistoricalOpen(), 0.01);
            Assert.assertEquals("Older day last price isn't accurate", olderDayQuotes.getClose().doubleValue()
                    , stockInformationPage.getHistoricalLast(), 0.01);

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
                , financialReportsPage.reportTitlesAreLinks());
        Assert.assertTrue("No financial reports links to a .pdf file."
                , financialReportsPage.pdfLinkIsPresent());
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
                , livePressReleases.pressReleasesAreAllFromYear(Year.now().toString()));
        livePressReleases.switchYearTo("2015");
        Assert.assertTrue("One or more displayed press releases are not from the selected year (2015)."
                , livePressReleases.pressReleasesAreAllFromYear("2015"));
        try {
            livePressReleases.openFirstPressRelease();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        Assert.assertTrue("Press release is not open."
                , livePressReleases.pressReleaseIsOpen());
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
                , liveEvents.allEventsAreUpcoming());
        liveEvents.switchToPastEvents();
        Assert.assertTrue("Past events are not displayed.", liveEvents.eventsAreDisplayed());
        try {
            Assert.assertTrue("One or more events displayed are not past.", liveEvents.allEventsArePast());
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        try{
              liveEvents.openFirstEvent();
        }catch (TimeoutException e){
             driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        try{
            Assert.assertTrue("Event details have not been loaded.", liveEvents.eventIsOpen());
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
                , livePresentations.presentationsAreAllFromYear(Year.now().toString()));
        livePresentations.switchYearTo("2015");
        Assert.assertTrue("One or more displayed presentations are not from the selected year (2015)."
                , livePresentations.presentationsAreAllFromYear("2015"));
        Assert.assertTrue("One or more presentation links are not links."
                , livePresentations.presentationLinksAreLinks());
        Assert.assertTrue("No presentations link to a .pdf file."
                , livePresentations.pdfLinkIsPresent());
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
                , secFilingsPage.filingsAreAllFromYear(Year.now().toString()));
        secFilingsPage.switchYearTo("2015");
        Assert.assertTrue("One or more displayed filings are not from the selected year (2015)."
                , secFilingsPage.filingsAreAllFromYear("2015"));
        Assert.assertTrue("One or more pdf icons do not link to a .pdf file."
                , secFilingsPage.pdfIconsLinkToPDF());
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
                , boardOfDirectorsPage.peopleHaveBiographicalInformation());
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
                , rssFeedsPage.pressReleaseRSSExists());
    }

    @Test
    public void rssEventsWorks(){
        Assert.assertTrue("RSS Feeds are not displayed."
                , new HomePage(driver).selectRSSFeedsFromMenu().rssFeedsExist());
        Assert.assertTrue("RSS Feeds for events do not open correctly", rssFeedsPage.eventRSSExists());
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
                , rssFeedsPage.presentationRSSExists());
    }

    @Test
    public void emailAlertsWork() {
        EmailAlertsPage emailAlertsPage = new HomePage(driver).selectEmailAlertsFromMenu();
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
        EmailAlertsPage emailAlertsPage = new HomePage(driver).selectEmailAlertsFromMenu();
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

    @Test
    public void faqPageWorks(){
        FAQPage faqPage = new HomePage(driver).selectFAQFromMenu();
        int numQuestionsTop = faqPage.getNumQuestionsTop();
        Assert.assertTrue("No questions are displayed at top of page.", numQuestionsTop > 0);
        int numQuestionsBelow = faqPage.getNumQuestionsBelow();
        Assert.assertEquals("Number of questions displayed at below is different from number of questions displayed at top of page",
                numQuestionsTop, numQuestionsBelow);
        int numAnswers = faqPage.getNumAnswers();
        Assert.assertEquals("There is not an answer for every question", numQuestionsBelow, numAnswers);
        Assert.assertEquals("Page does not scroll down after clicking question", faqPage.getFirstQuestionY(), faqPage.clickFirstQuestion().getScrollPositionY());
        Assert.assertEquals("Page does not scroll back up after clicking 'back to top'", 0, faqPage.clickBackToTop().getScrollPositionY());
    }

}
