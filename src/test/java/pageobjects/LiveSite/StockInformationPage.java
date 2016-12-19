package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by jasons on 2016-11-07.
 */
public class StockInformationPage extends AbstractPageObject {

    private static By stockChartXignite;


    private final By stockChartXigniteGridArea;
    private final By timeRangeButtonXignite;
    private final By chartXigniteSlider;
    private final By chartXigniteTooltip;

    private final By stockQuote;
    private final By stockQuotePrice;
    private final By stockQuoteChange;
    private final By stockQuoteVolume;  //TODO remove all the By.cssSelector stuffs
    private final By stockQuotePChange;
    private final By stockQuoteDayHigh;
    private final By stockQuote52WeekHigh;
    private final By stockQuoteDayLow;
    private final By stockQuote52WeekLow;
    private final By stockQuoteTodayOpen;
    private final By stockQuotePreviousClose;

    private final By stockChartTickertechFrame;
    private final By stockChartTickertech;
    private final By timeRangeButtonTickertech;
    private final By chartByButtonTickertech;
    private final By chartTypeButtonTickertech;
    private final By compareStock;
    private final By dowCheckbox;
    private final By nasdaqCheckbox;
    private final By spCheckbox;
    private final By russellCheckbox;
    private final By tickertechCompareButton;

    private final By historicalQuotes;
    private final By historicalHigh;
    private final By historicalLow;
    private final By historicalVolume;
    private final By historicalOpen;
    private final By historicalLast;
    private final By historicalMonthSelector;
    private final By historicalDaySelector;
    private final By historicalYearSelector;
    private final By lookupButton;

    Actions actions = new Actions(driver);

    public StockInformationPage(WebDriver driver) {
        super(driver);
        stockChartXignite = By.className(propUIPublicSite.getProperty("stockChartXignite"));
        stockChartXigniteGridArea = By.cssSelector(propUIPublicSite.getProperty("stockChartXigniteGridArea"));
        timeRangeButtonXignite = By.className (propUIPublicSite.getProperty("timeRangeButtonXignite"));
        chartXigniteSlider = By.cssSelector (propUIPublicSite.getProperty("chartXigniteSlider"));
        chartXigniteTooltip = By.className (propUIPublicSite.getProperty("chartXigniteTooltip"));

        stockQuote = By.className(propUIPublicSite.getProperty("stockQuote"));
        stockQuotePrice = By.className(propUIPublicSite.getProperty("stockQuotePrice"));
        stockQuoteChange = By.cssSelector(propUIPublicSite.getProperty("stockQuoteChange"));
        stockQuoteVolume = By.cssSelector(propUIPublicSite.getProperty("stockQuoteVolume"));
        stockQuotePChange = By.cssSelector(propUIPublicSite.getProperty("stockQuotePChange"));
        stockQuoteDayHigh = By.cssSelector(propUIPublicSite.getProperty("stockQuoteDayHigh"));
        stockQuote52WeekHigh = By.cssSelector(propUIPublicSite.getProperty("stockQuote52WeekHigh"));
        stockQuoteDayLow = By.cssSelector (propUIPublicSite.getProperty("stockQuoteDayLow"));
        stockQuote52WeekLow = By.cssSelector (propUIPublicSite.getProperty("stockQuote52WeekLow"));
        stockQuoteTodayOpen = By.cssSelector (propUIPublicSite.getProperty("stockQuoteTodayOpen"));
        stockQuotePreviousClose = By.cssSelector (propUIPublicSite.getProperty("stockQuotePreviousClose"));

        stockChartTickertechFrame = By.id (propUIPublicSite.getProperty("stockChartTickertechFrame"));
        stockChartTickertech = By.cssSelector (propUIPublicSite.getProperty("stockChartTickertech"));
        timeRangeButtonTickertech = By.cssSelector (propUIPublicSite.getProperty("timeRangeButtonTickertech"));
        chartByButtonTickertech = By.cssSelector (propUIPublicSite.getProperty("chartByButtonTickertech"));
        chartTypeButtonTickertech = By.cssSelector (propUIPublicSite.getProperty("chartTypeButtonTickertech"));
        compareStock = By.cssSelector (propUIPublicSite.getProperty("compareStock"));
        dowCheckbox = By.cssSelector (propUIPublicSite.getProperty("dowCheckbox"));
        nasdaqCheckbox = By.cssSelector (propUIPublicSite.getProperty("nasdaqCheckbox"));
        spCheckbox = By.cssSelector (propUIPublicSite.getProperty("spCheckbox"));
        russellCheckbox = By.cssSelector (propUIPublicSite.getProperty("russellCheckbox"));
        tickertechCompareButton = By.cssSelector (propUIPublicSite.getProperty("tickertechCompareButton"));

        historicalQuotes = By.className (propUIPublicSite.getProperty("historicalQuotes"));
        historicalHigh = By.cssSelector (propUIPublicSite.getProperty("historicalHigh"));
        historicalLow = By.cssSelector (propUIPublicSite.getProperty("historicalLow"));
        historicalVolume = By.cssSelector (propUIPublicSite.getProperty("historicalVolume"));
        historicalOpen = By.cssSelector (propUIPublicSite.getProperty("historicalOpen"));
        historicalLast = By.cssSelector (propUIPublicSite.getProperty("historicalLast"));
        historicalMonthSelector = By.xpath (propUIPublicSite.getProperty("historicalMonthSelector"));
        historicalDaySelector = By.xpath (propUIPublicSite.getProperty("historicalDaySelector"));
        historicalYearSelector = By.xpath (propUIPublicSite.getProperty("historicalYearSelector"));
        lookupButton = By.xpath (propUIPublicSite.getProperty("lookupButton"));

    }

    // XIGNITE STOCK CHART METHODS

    public boolean stockChartXigniteIsDisplayed(){
        return doesElementExist(stockChartXignite);
    }

    public void switchChartXigniteTo1Month(){
        findElements(timeRangeButtonXignite).get(0).click();
    }

    public void switchChartXigniteTo1Quarter(){
        findElements(timeRangeButtonXignite).get(1).click();
    }

    public void switchChartXigniteTo1Year(){
        findElements(timeRangeButtonXignite).get(2).click();
    }

    public int getChartXigniteSliderXStart(){
        return Integer.parseInt(findElement(chartXigniteSlider).getAttribute("x"));
    }

    public boolean canHoverOverChartXignite(){
        actions.clickAndHold(findElement(stockChartXigniteGridArea)).perform();
        pause(5000L);//ADD a WAIT FOR THE ATTRIBUTE "TRANSFORM" TO NOT BE -9999 perhaps? replace with pause
        return !findElement(chartXigniteTooltip).getAttribute("transform").contains("-9999");
    }

    // STOCK QUOTE METHODS

    public boolean stockQuoteIsDisplayed(){
        return doesElementExist(stockQuote);
    }

    public boolean stockQuoteValuesArePresent(){
        boolean valuesArePresent = true;

        if (!findElement(stockQuotePrice).isDisplayed()){
            System.out.println("Stock price is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuotePrice).getText()) <= 0){
            System.out.println("Stock price ("+findElement(stockQuotePrice).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuoteChange).isDisplayed()){
            System.out.println("Stock change is not displayed.");
            valuesArePresent = false;
        }
        try {
            if (!findElement(stockQuoteVolume).isDisplayed()){
                System.out.println("Stock volume is not displayed.");
                valuesArePresent = false;
            }
            else if (NumberFormat.getNumberInstance(Locale.US).parse(findElement(stockQuoteVolume).getText()).intValue() < 0){
                System.out.println("Stock volume ("+findElement(stockQuoteVolume).getText()+") is not a positive number.");
                valuesArePresent = false;
            }
        }catch (ParseException e){
            System.out.println("Stock volume ("+findElement(stockQuoteVolume).getText()+") is not a number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuotePChange).isDisplayed()){
            System.out.println("Stock % change is not displayed.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuoteDayHigh).isDisplayed()){
            System.out.println("Stock intraday high is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuoteDayHigh).getText()) <= 0){
            System.out.println("Stock intraday high ("+findElement(stockQuoteDayHigh).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuote52WeekHigh).isDisplayed()){
            System.out.println("Stock 52 week high is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuote52WeekHigh).getText()) <= 0){
            System.out.println("Stock 52 week high ("+findElement(stockQuote52WeekHigh).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuoteDayLow).isDisplayed()){
            System.out.println("Stock intraday low is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuoteDayLow).getText()) <= 0){
            System.out.println("Stock intraday low ("+findElement(stockQuoteDayLow).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuote52WeekLow).isDisplayed()){
            System.out.println("Stock 52 week low is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuote52WeekLow).getText()) <= 0){
            System.out.println("Stock 52 week low ("+findElement(stockQuote52WeekLow).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuoteTodayOpen).isDisplayed()){
            System.out.println("Stock today's open is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuoteTodayOpen).getText()) <= 0){
            System.out.println("Stock today's open ("+findElement(stockQuoteTodayOpen).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(stockQuotePreviousClose).isDisplayed()){
            System.out.println("Stock previous close is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(stockQuotePreviousClose).getText()) <= 0){
            System.out.println("Stock previous close ("+findElement(stockQuotePreviousClose).getText()+") is not a positive number.");
            valuesArePresent = false;
        }

        return valuesArePresent;
    }

    public double getStockPrice(){
        return Double.parseDouble(findElement(stockQuotePrice).getText());
    }

    public double getStockChange(){
        return Double.parseDouble(findElement(stockQuoteChange).getText());
    }

    public double getStockPChange(){
        String PChange = findElement(stockQuotePChange).getText();
        return Double.parseDouble(PChange.substring(0,PChange.indexOf('%')));
    }

    public double getStockDayHigh(){
        return Double.parseDouble(findElement(stockQuoteDayHigh).getText());
    }

    public double getStock52WeekHigh(){
        return Double.parseDouble(findElement(stockQuote52WeekHigh).getText());
    }

    public double getStockDayLow(){
        return Double.parseDouble(findElement(stockQuoteDayLow).getText());
    }

    public double getStock52WeekLow(){
        return Double.parseDouble(findElement(stockQuote52WeekLow).getText());
    }

    public double getStockTodayOpen(){
        return Double.parseDouble(findElement(stockQuoteTodayOpen).getText());
    }

    public double getStockPreviousClose(){
        return Double.parseDouble(findElement(stockQuotePreviousClose).getText());
    }

    // TICKERTECH STOCK CHART METHODS

    public boolean stockChartTickertechIsDisplayed(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        boolean isDisplayed = findElement(stockChartTickertech).isDisplayed();
        driver.switchTo().defaultContent();
        return isDisplayed;
    }

    public String getTickertechSRC(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        String src = findElement(stockChartTickertech).getAttribute("src");
        driver.switchTo().defaultContent();
        return src;
    }

    public void switchChartTickertechTo1Month(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(timeRangeButtonTickertech).get(0).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechTo1Quarter(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(timeRangeButtonTickertech).get(1).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechTo1Year(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(timeRangeButtonTickertech).get(2).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToPrice(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartByButtonTickertech).get(0).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToPChange(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartByButtonTickertech).get(1).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToMountain(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(0).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToFillToPrevClose(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(1).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToLine(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(2).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToPoint(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(3).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToBar(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(4).click();
        driver.switchTo().defaultContent();
    }

    public void switchChartTickertechToCandleStick(){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(5).click();
        driver.switchTo().defaultContent();
    }

    public void tickertechCompareVs(String stock, boolean dow, boolean nasdaq, boolean sp, boolean russell){
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElement(compareStock).sendKeys(stock);
        if (dow ^ findElement(dowCheckbox).isSelected()){
            findElement(dowCheckbox).click();
        }
        if (nasdaq ^ findElement(nasdaqCheckbox).isSelected()){
            findElement(nasdaqCheckbox).click();
        }
        if (sp ^ findElement(spCheckbox).isSelected()){
            findElement(spCheckbox).click();
        }
        if (russell ^ findElement(russellCheckbox).isSelected()){
            findElement(russellCheckbox).click();
        }
        findElement(tickertechCompareButton).click();
        driver.switchTo().defaultContent();
    }

    // HISTORICAL QUOTE METHODS

    public boolean historicalQuotesAreDisplayed(){
        return doesElementExist(historicalQuotes);
    }

    public boolean historicalQuoteValuesArePresent(){
        boolean valuesArePresent = true;

        if (!findElement(historicalHigh).isDisplayed()){
            System.out.println("Historical high is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(historicalHigh).getText()) <= 0){
            System.out.println("Historical high ("+findElement(historicalHigh).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(historicalLow).isDisplayed()){
            System.out.println("Historical low is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(historicalLow).getText()) <= 0){
            System.out.println("Historical low ("+findElement(historicalLow).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        try {
            if (!findElement(historicalVolume).isDisplayed()){
                System.out.println("Historical volume is not displayed.");
                valuesArePresent = false;
            }
            else if (NumberFormat.getNumberInstance(Locale.US).parse(findElement(historicalVolume).getText()).intValue() < 0){
                System.out.println("Historical volume ("+findElement(historicalVolume).getText()+") is not a positive number.");
                valuesArePresent = false;
            }
        }catch (ParseException e){
            System.out.println("Historical volume ("+findElement(historicalVolume).getText()+") is not a number.");
            valuesArePresent = false;
        }
        if (!findElement(historicalOpen).isDisplayed()){
            System.out.println("Historical opening price is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(historicalOpen).getText()) <= 0){
            System.out.println("Historical opening price ("+findElement(historicalOpen).getText()+") is not a positive number.");
            valuesArePresent = false;
        }
        if (!findElement(historicalLast).isDisplayed()){
            System.out.println("Historical last is not displayed.");
            valuesArePresent = false;
        }
        else if (Double.parseDouble(findElement(historicalLast).getText()) <= 0){
            System.out.println("Historical last ("+findElement(historicalLast).getText()+") is not a positive number.");
            valuesArePresent = false;
        }

        return valuesArePresent;
    }

    public String[] getHistoricalQuote(){
        return new String[] {
                findElement(historicalHigh).getText(),
                findElement(historicalLow).getText(),
                findElement(historicalVolume).getText(),
                findElement(historicalOpen).getText(),
                findElement(historicalLast).getText()
        };
    }

    public void changeQuoteDate(){
        Select monthDropdown = new Select(findElement(historicalMonthSelector));
        Select dayDropdown = new Select(findElement(historicalDaySelector));
        Select yearDropdown = new Select(findElement(historicalYearSelector));
        monthDropdown.selectByValue("5");
        dayDropdown.selectByValue("25");
        yearDropdown.selectByValue("2016");
        findElement(lookupButton).click();
    }

    public Calendar getCurrentDate(){
        Select monthDropdown = new Select(findElement(historicalMonthSelector));
        Select dayDropdown = new Select(findElement(historicalDaySelector));
        Select yearDropdown = new Select(findElement(historicalYearSelector));
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Integer.parseInt(yearDropdown.getFirstSelectedOption().getAttribute("value")),
                Integer.parseInt(monthDropdown.getFirstSelectedOption().getAttribute("value"))-1,
                Integer.parseInt(dayDropdown.getFirstSelectedOption().getAttribute("value")));
        return calendar;
    }

    public double getHistoricalHigh(){
        return Double.parseDouble(findElement(historicalHigh).getText());
    }

    public double getHistoricalLow(){
        return Double.parseDouble(findElement(historicalLow).getText());
    }

    public double getHistoricalVolume(){
        try {
            return NumberFormat.getNumberInstance(Locale.US).parse(findElement(historicalVolume).getText()).doubleValue();
        }catch (ParseException e){
            System.out.println("Historical volume ("+findElement(historicalVolume).getText()+") is not a number.");
            return -1;
        }
    }

    public double getHistoricalOpen(){
        return Double.parseDouble(findElement(historicalOpen).getText());
    }

    public double getHistoricalLast(){
        return Double.parseDouble(findElement(historicalLast).getText());
    }

}
