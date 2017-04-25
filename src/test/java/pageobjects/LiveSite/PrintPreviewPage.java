package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sarahr on 4/11/2017.
 */

public class PrintPreviewPage extends AbstractPageObject {

    //Selectors
    By PrintPreviewBtn = By.xpath("//a[contains(@class,'PrintPageLink')]");
    By PaneContent = By.xpath("//div[contains(@class,'PaneContent')]");

    public PrintPreviewPage(WebDriver driver){
        super(driver);
    }

    public boolean checkHTTPResponse(String link){
        try{
            int code = 0;
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            code = connection.getResponseCode();
            if(code == 200){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            return false;
        }
    }

    public void clickPrintPreviewBtn(){
        findVisibleElement(PrintPreviewBtn).click();
        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        //Switch to new window
        driver.switchTo().window(tabs.get(1));

    }

    public boolean contentIsSameOnPP(){
        String before, after;

        try {
            before = findVisibleElement(PaneContent).getText();
            clickPrintPreviewBtn();
            after = findElement(PaneContent).getText();
        }
        catch(Exception e){
            return false;
        }

        if(before.equalsIgnoreCase(after)){
            return true;
        }
        else{
            return false;
        }
    }

}
