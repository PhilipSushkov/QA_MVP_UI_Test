package pageobjects.LiveSite;

import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sarahr on 4/11/2017.
 */
public class PrintPreviewPage extends AbstractPageObject {

    //Selectors

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

    public void printWorks(){

    }
}
