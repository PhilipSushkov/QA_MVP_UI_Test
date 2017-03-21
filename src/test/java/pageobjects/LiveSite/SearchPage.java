package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Created by sarahr on 3/17/2017.
 */
public class SearchPage extends AbstractPageObject{

    By presentationExample = By.xpath("//div[contains(@class,'ModuleItem')][1]//span[contains(@class,'ModuleHeadline')]");
    By searchBar = By.xpath("//input[contains(@class,'SearchInput')]");
    By searchButton = By.xpath("//input[contains(@class,'SearchButton')]");
    By searchSummaryResults = By.xpath("//div[contains(@class,'SearchSummary')]");
    By searchModuleTitle = By.xpath("//span[contains(@class,'ModuleTitle') and contains(string(),'Search Results')]");
    By pageTwoResults = By.xpath("//div[contains(@class,'SearchResultsPaging')]//a[contains(string(),'2')]");
    By previewToolbar = By.xpath("//div[contains(@class,'PreviewToolBar')]");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void removePreviewToolbar(){
        WebElement element = driver.findElement(previewToolbar);
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='hidden'", element);
    }

    public String searchSomething(String searchTerm){
        waitForElement(searchBar);
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForElement(searchButton);
        findElement(searchButton).click();

        return findElement(searchSummaryResults).getText();
    }

    public boolean isTermInResults(String searchTerm){
        By term = By.xpath("//div[contains(@class,'TabTableItem') and contains(string()," + " '" + searchTerm + "'" + ")]");

        try{
            findElement(term);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    public String getSearchableTerm(){
        //go to the page
        LivePresentations presentationPage = new HomePage(driver).selectPresentationsFromMenu();
        //get the text from the thing
        waitForElement(presentationExample);
        String presTitle = findElement(presentationExample).getText();

        return presTitle;
    }

    public boolean goToNextPage(){
        waitForElement(pageTwoResults);
        findElement(pageTwoResults).click();

        By firstResult = By.xpath("//div[contains(@class,'SearchResultsContainer')]//div[contains(@class,'TabTableItem')][1]");

        try{
            findElement(firstResult);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

}
