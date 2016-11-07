package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-07.
 */
public class StockInformationPage extends AbstractPageObject {

    private final By stockChart = By.className("highcharts-container");
    private final By stockChartGridArea = By.cssSelector(".highcharts-background+rect");
    private final By timeRangeButton = By.className("highcharts-button");
    private final By chartSlider = By.cssSelector(".highcharts-navigator rect");
    private final By chartTooltip = By.className("highcharts-tooltip");

    Actions actions = new Actions(driver);

    public StockInformationPage(WebDriver driver) {
        super(driver);
    }

    // STOCK CHART METHODS

    public boolean stockChartIsDisplayed(){
        return doesElementExist(stockChart);
    }

    public void switchChartTo1Month(){
        findElements(timeRangeButton).get(0).click();
    }

    public void switchChartTo1Quarter(){
        findElements(timeRangeButton).get(1).click();
    }

    public void switchChartTo1Year(){
        findElements(timeRangeButton).get(2).click();
    }

    public String getChartSliderXStart(){
        return findElement(chartSlider).getAttribute("x");
    }

    public boolean canHoverOverChart(){
        actions.clickAndHold(findElement(stockChartGridArea)).perform();
        return !findElement(chartTooltip).getAttribute("transform").contains("-9999");
    }

}
