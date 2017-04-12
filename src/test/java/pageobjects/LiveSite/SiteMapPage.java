package pageobjects.LiveSite;

import org.apache.maven.model.Site;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import static specs.AbstractSpec.propUIPublicSite;
import java.net.HttpURLConnection;
import java.net.*;

/**
 * Created by sarahr on 4/6/2017.
 */
public class SiteMapPage extends AbstractPageObject{

    //selectors
    By siteMapTitle = By.xpath("//span[contains(@class,'ModuleTitle') and contains(string(),'Site Map')]");
    private final By InvestorPage = By.className("SiteMapLink_Investors");
    private final By FinancialReportsPage = By.className("SiteMapLink_financial-reports");
    private final By EmailAlertsPage = By.className("SiteMapLink_email-alerts");
    private final By EventsPage = By.className("SiteMapLink_Events");
    private final By PresentationsPage = By.className("SiteMapLink_Presentations");

    public SiteMapPage(WebDriver driver) {
        super(driver);
    }

    public boolean pageNotFoundRedirects(String url){

        driver.get("http://chicagotest.q4web.com/"+url);
        System.out.println("Site visited: "+"http://chicagotest.q4web.com/"+url);

        try{
            findVisibleElement(siteMapTitle);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    public boolean checkBtnResponseCode(){

        By SiteMapButtons[] = {InvestorPage, FinancialReportsPage, EmailAlertsPage, EventsPage, PresentationsPage};

        for(int i = 0; i < 5; i++) {
            findVisibleElement(SiteMapButtons[i]).click();
            String link = driver.getCurrentUrl();

            try {
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int code = connection.getResponseCode();

                if (code == 200) {
                    //good!
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            driver.get("https://chicagotest.q4web.com/English/Site-Map/default.aspx");
        }
        return true;
    }


}
