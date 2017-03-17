package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by sarahr on 3/17/2017.
 */
public class SearchPage extends AbstractPageObject{

    By presentationExample = By.xpath("//div[contains(@class,'ModuleItem')][1]//span[contains(@class,'ModuleHeadline')]");
    By searchBar = By.xpath("//input[contains(@class,'SearchInput')]");
    By searchButton = By.xpath("//input[contains(@class,'SearchButton')]");


    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void searchSomething(String searchTerm){
        waitForElement(searchBar);
        findElement(searchBar).click();
        findElement(searchBar).clear();
        findElement(searchBar).sendKeys(searchTerm);

        waitForElement(searchButton);
        findElement(searchButton).click();
    }

    public String getSearchableTerm(){
        //go to the page
        LivePresentations presentationPage = new HomePage(driver).selectPresentationsFromMenu();
        //get the text from the thing
        waitForElement(presentationExample);
        String presTitle = findElement(presentationExample).getText();

        return presTitle;
    }

}
