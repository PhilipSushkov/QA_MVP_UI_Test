package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by jasons on 2016-11-28.
 */
public class InvestmentCalculatorPage extends AbstractPageObject {

    private final By calculator = By.className("calc-widget-investment-calculator");
    private final By amountInput = By.className("calc-amount");
    private final By startDateInput = By.className("calc-startDate");
    private final By endDateInput = By.className("calc-endDate");
    private final By compareToOtherCheckbox = By.cssSelector(".calc-input:nth-child(4) [type=checkbox]");
    private final By compareToOtherText = By.cssSelector(".calc-input:nth-child(4) [type=text]");
    private final By compareToOtherFirstOption = By.className("ui-menu-item");
    private final By calculateButton = By.className("calc-button");
    private final By resultsArea = By.className("fancybox-desktop");
    private final By closeResultsButton = By.className("fancybox-close");
    private final By growthChart = By.className("calc-chart");
    private final By chartHoverText = By.className("highcharts-tooltip");
    private final By growthData = By.className("calc-info");
    private final By growthDataRow = By.cssSelector(".calc-info tr");
    private final By startDateResults = By.cssSelector(".calc-info tr:nth-child(2) td:nth-child(2)");
    private final By endDateResults = By.cssSelector(".calc-info tr:nth-child(3) td:nth-child(2)");

    DateTimeFormatter longDate = DateTimeFormatter.ofPattern("MMMM d, yyyy");
    DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("MMM d, yyyy");

    Actions actions = new Actions(driver);

    public InvestmentCalculatorPage(WebDriver driver) {
        super(driver);
    }

    public boolean investmentCalculatorExists(){
        return doesElementExist(calculator) && findElement(calculator).isDisplayed();
    }

    // performs a calculation of the investment of $100 with specified start and end dates
    public InvestmentCalculatorPage performSampleCalculation(LocalDate startDate, LocalDate endDate){
        waitForElement(amountInput);
        findElement(amountInput).clear();
        findElement(amountInput).sendKeys("100");
        findElement(startDateInput).clear();
        findElement(startDateInput).sendKeys(startDate.format(longDate));
        findElement(endDateInput).clear();
        findElement(endDateInput).sendKeys(endDate.format(longDate));
        findElement(calculateButton).click();
        return this;
    }

    public InvestmentCalculatorPage recalculateAndCompareTo(String symbol){
        waitForElement(compareToOtherCheckbox);
        findElement(compareToOtherCheckbox).click();
        waitForElementToAppear(compareToOtherText);
        findElement(compareToOtherText).sendKeys(symbol);
        waitForElementToAppear(compareToOtherFirstOption);
        findElement(compareToOtherFirstOption).click();
        findElement(calculateButton).click();
        return this;
    }

    public InvestmentCalculatorPage closeResults(){
        waitForElement(closeResultsButton);
        findElement(closeResultsButton).click();
        pause(500);
        return this;
    }

    public boolean resultsAreOpen(){
        return doesElementExist(resultsArea);
    }

    public boolean growthChartIsPresent(){
        return doesElementExist(growthChart) && findElement(growthChart).isDisplayed();
    }

    public boolean canHoverOverGrowthChart(){
        actions.clickAndHold(findElement(growthChart)).perform(); //clickAndHold needed so that cursor is still there when getAttribute is run
        pause(5000L);
        return !findElement(chartHoverText).getAttribute("transform").contains("-9999"); //transform attribute contains "-9999" when hovertext is not visible
    }

    // checks whether the provided symbol is displayed in the table header
    public boolean resultsIncludeSymbol(String symbol){
        return findElements(growthDataRow).get(0).getText().contains(symbol.toUpperCase());
    }

    public LocalDate getStartDate(){
        return LocalDate.parse(findElement(startDateResults).getText(), shortDate);
    }

    public LocalDate getEndDate(){
        return LocalDate.parse(findElement(endDateResults).getText(), shortDate);
    }

    public boolean growthDataIsValid(){
        if (!(doesElementExist(growthData) && findElement(growthChart).isDisplayed())){
            System.out.println("Growth data is not displayed.");
            return false;
        }
        if (!findElements(growthDataRow).get(0).getText().matches("Breakdown [A-Z]{1,5}:[A-Z]{1,5}")){
            System.out.println("Header row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(0).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(1).getText().matches("Start Date [A-Z][a-z]{2} \\d{1,2}, \\d{4}")){
            System.out.println("'Start Date' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(1).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(2).getText().matches("End Date [A-Z][a-z]{2} \\d{1,2}, \\d{4}")){
            System.out.println("'End Date' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(2).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(3).getText().matches("Start Price/Share \\$\\d{1,4}.\\d{1,2}")){
            System.out.println("'Start Price' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(3).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(4).getText().matches("End Price/Share \\$\\d{1,4}.\\d{1,2}")){
            System.out.println("'End Price' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(4).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(5).getText().matches("Total Return -?\\d{1,4}.\\d{2}%")){
            System.out.println("'Total Return' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(5).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(6).getText().matches("Compound Annual Growth Rate -?\\d{1,4}.\\d{2}%")){
            System.out.println("'Compound Annual Growth Rate' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(6).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(7).getText().matches("Starting Investment \\$(\\d{1,3},)?\\d{1,3}\\.\\d{2}")){
            System.out.println("'Starting Investment' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(7).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(8).getText().matches("Ending Investment \\$(\\d{1,3},)?\\d{1,3}\\.\\d{2}")){
            System.out.println("'Ending Investment' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(8).getText());
            return false;
        }
        if (!findElements(growthDataRow).get(9).getText().matches("Years \\d(\\.\\d)?")){
            System.out.println("'Years' row of growth data table is wrong.\n\tDisplayed: "+findElements(growthDataRow).get(9).getText());
            return false;
        }

        return true;
    }
}
