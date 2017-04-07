package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by sarahr on 4/6/2017.
 */
public class SiteMapPage extends AbstractPageObject{

    //selectors
    By siteMapTitle = By.xpath("//span[contains(@class,'ModuleTitle') and contains(string(),'Site Map')]");

    public SiteMapPage(WebDriver driver) {
        super(driver);
    }

    public boolean pageNotFoundRedirects(String url){

        driver.get("http://chicagotest.q4web.com/"+url);

        try{
            findVisibleElement(siteMapTitle);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

}
