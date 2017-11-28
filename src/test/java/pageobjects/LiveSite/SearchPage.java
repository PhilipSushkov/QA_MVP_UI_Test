package pageobjects.LiveSite;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

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
    By moduleTitle = By.xpath("//div[contains(@class,'SearchResultsContainer')]//span[contains(@class,'ModuleTitle')]");


    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void removePreviewToolbar(){
        WebElement element = driver.findElement(previewToolbar);
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='hidden'", element);
    }

    public String searchSomething(String searchTerm){
        waitForElement(searchBar);
        findVisibleElement(searchBar).click();
        findVisibleElement(searchBar).clear();
        findVisibleElement(searchBar).sendKeys(searchTerm);

        waitForElement(searchButton);
        findVisibleElement(searchButton).click();

        return findVisibleElement(searchSummaryResults).getText();
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

    public String getModuleTitle(){
        try{
            waitForElement(moduleTitle);
            return findElement(moduleTitle).getText();
        } catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean getPopUpWindow(){
        try{
            driver.switchTo().alert();
            // If it reaches here, it found a popup
        } catch(org.openqa.selenium.NoAlertPresentException e){
            return false;
        }
        return true;
    }

}
