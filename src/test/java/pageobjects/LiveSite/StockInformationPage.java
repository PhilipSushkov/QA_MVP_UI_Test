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

/**
 * Created by jasons on 2016-11-07.
 */
public class StockInformationPage extends AbstractPageObject {

    private final By stockChartXignite = By.className("highcharts-container");
    private final By stockChartXigniteGridArea = By.cssSelector(".highcharts-background+rect");
    private final By timeRangeButtonXignite = By.className("highcharts-button");
    private final By chartXigniteSlider = By.cssSelector(".highcharts-navigator rect");
    private final By chartXigniteTooltip = By.className("highcharts-tooltip");

    private final By stockQuote = By.className("StockQuoteContainer");
    private final By stockQuotePrice = By.className("Price");
    private final By stockQuoteChange = By.cssSelector(".ChangeLabel:last-child");
    private final By stockQuoteVolume = By.cssSelector(".StockData.Volume");
    private final By stockQuotePChange = By.cssSelector(".PChangeLabel:last-child");
    private final By stockQuoteDayHigh = By.cssSelector(".StockData.High");
    private final By stockQuote52WeekHigh = By.cssSelector(".StockData.WeekHigh");
    private final By stockQuoteDayLow = By.cssSelector(".StockData.Low");
    private final By stockQuote52WeekLow = By.cssSelector(".StockData.WeekLow");
    private final By stockQuoteTodayOpen = By.cssSelector(".StockData.TodaysOpen");
    private final By stockQuotePreviousClose = By.cssSelector(".StockData.PreviousClose");

    private final By stockChartTickertechFrame = By.id("stock-chart-frame");
    private final By stockChartTickertech = By.cssSelector("[name=ctchart]");
    private final By timeRangeButtonTickertech = By.cssSelector("[href*=handleMouseClick]");
    private final By chartByButtonTickertech = By.cssSelector("[href*=chartPrice]");
    private final By chartTypeButtonTickertech = By.cssSelector("[href*=clickType]");
    private final By compareStock = By.cssSelector("[name=compare]");
    private final By dowCheckbox = By.cssSelector("[name=comparedji]");
    private final By nasdaqCheckbox = By.cssSelector("[name=comparecomp]");
    private final By spCheckbox = By.cssSelector("[name=comparespx]");
    private final By russellCheckbox = By.cssSelector("[name=comparerut]");
    private final By tickertechCompareButton = By.cssSelector("[type=submit]");

    private final By historicalQuotes = By.className("StockHistorical");
    private final By historicalHigh = By.cssSelector(".StockHistorical .High");
    private final By historicalLow = By.cssSelector(".StockHistorical .Low");
    private final By historicalVolume = By.cssSelector(".StockHistorical .Volume");
    private final By historicalOpen = By.cssSelector(".StockHistorical .TodaysOpen");
    private final By historicalLast = By.cssSelector(".StockHistorical .PreviousClose");
    private final By historicalMonthSelector = By.xpath("//select[contains(@id,'ddlMonth')]");
    private final By historicalDaySelector = By.xpath("//select[contains(@id,'ddlDay')]");
    private final By historicalYearSelector = By.xpath("//select[contains(@id,'ddlYear')]");
    private final By lookupButton = By.xpath("//input[contains(@id,'btnLookup')]");

    Actions actions = new Actions(driver);

    public StockInformationPage(WebDriver driver) {
        super(driver);
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
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        return findElement(stockChartTickertech).isDisplayed();
    }

    public String getTickertechSRC(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        return findElement(stockChartTickertech).getAttribute("src");
    }

    public void switchChartTickertechTo1Month(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(timeRangeButtonTickertech).get(0).click();
    }

    public void switchChartTickertechTo1Quarter(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(timeRangeButtonTickertech).get(1).click();
    }

    public void switchChartTickertechTo1Year(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(timeRangeButtonTickertech).get(2).click();
    }

    public void switchChartTickertechToPrice(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartByButtonTickertech).get(0).click();
    }

    public void switchChartTickertechToPChange(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartByButtonTickertech).get(1).click();
    }

    public void switchChartTickertechToMountain(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(0).click();
    }

    public void switchChartTickertechToFillToPrevClose(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(1).click();
    }

    public void switchChartTickertechToLine(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(2).click();
    }

    public void switchChartTickertechToPoint(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(3).click();
    }

    public void switchChartTickertechToBar(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(4).click();
    }

    public void switchChartTickertechToCandleStick(){
        driver.switchTo().defaultContent();
        driver.switchTo().frame(findElement(stockChartTickertechFrame));
        findElements(chartTypeButtonTickertech).get(5).click();
    }

    public void tickertechCompareVs(String stock, boolean dow, boolean nasdaq, boolean sp, boolean russell){
        driver.switchTo().defaultContent();
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
