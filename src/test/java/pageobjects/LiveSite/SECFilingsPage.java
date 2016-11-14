package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.util.List;

/**
 * Created by jasons on 2016-11-11.
 */
public class SECFilingsPage extends AbstractPageObject {

    private final By filingDate = By.className("ItemDate");
    private final By yearLink = By.className("ModuleYearLink");
    private final By pdfIcon = By.cssSelector(".PdfIcon a");

    public SECFilingsPage(WebDriver driver) {
        super(driver);
    }

    public boolean filingsAreDisplayed(){
        return doesElementExist(filingDate) && findElement(filingDate).isDisplayed();
    }

    public boolean filingsAreAllFromYear(String year){
        boolean allFromYear = true;
        List<WebElement> filingDates = findElements(filingDate);
        for (int i=0; i<filingDates.size(); i++){
            if (!filingDates.get(i).getText().contains(year)){
                System.out.println("Filing with date: "+filingDates.get(i).getText()+" is not in year "+year);
                allFromYear = false;
            }
        }
        return allFromYear;
    }

    public void switchYearTo(String year){
        List<WebElement> yearLinks = findElements(yearLink);
        for (int i=0; i<yearLinks.size(); i++){
            if (yearLinks.get(i).getText().equals(year)){
                yearLinks.get(i).click();
                return;
            }
        }
    }

    public boolean pdfIconsLinkToPDF(){
        boolean allLinks = true;

        List<WebElement> filingDates = findElements(filingDate);
        List<WebElement> pdfIcons = findElements(pdfIcon);
        for (int i=0; i<pdfIcons.size(); i++){
            if (!pdfIcons.get(i).getAttribute("href").endsWith(".pdf")){
                System.out.println("Filing with date: "+filingDates.get(i).getText()+" has a pdf icon that does not link to a .pdf file.\n\thref = "+pdfIcons.get(i).getAttribute("href"));
                allLinks = false;
            }
        }

        return allLinks;
    }
}
