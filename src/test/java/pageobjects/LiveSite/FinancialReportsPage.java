package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by jasons on 2016-11-09.
 */
public class FinancialReportsPage extends AbstractPageObject {

    private final By reportTitle = By.className("ReportLink");

    public FinancialReportsPage(WebDriver driver) {
        super(driver);
    }

    public boolean financialReportsAreDisplayed(){
        if (doesElementExist(reportTitle)){
            if (findElement(reportTitle).isDisplayed()){
                return true;
            }
        }
        return false;
    }

    public boolean reportTitlesAreLinks(){
        boolean allLinks = true;

        List<WebElement> reportTitles = findElements(reportTitle);
        for (int i=0; i<reportTitles.size(); i++){
            if (!reportTitles.get(i).getAttribute("href").contains("//")){
                System.out.println("Report '"+reportTitles.get(i).getText()+"' does not have valid link.\n\thref = "+reportTitles.get(i).getAttribute("href"));
                allLinks = false;
            }
        }

        return allLinks;
    }

    public boolean pdfLinkIsPresent(){
        List<WebElement> reportTitles = findElements(reportTitle);
        for (int i=0; i<reportTitles.size(); i++){
            if (reportTitles.get(i).getAttribute("href").endsWith(".pdf")){
                return true;
            }
        }
        return false;
    }
}
