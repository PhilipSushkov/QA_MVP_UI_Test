package pageobjects.api.AdminWeb.EuroNews;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.ApiAbstractSpec.propAPI;

import net.lightbody.bmp.core.har.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pageobjects.api.AdminWeb.ResponseDataObj;
import util.Functions;

import java.io.IOException;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class EuroNews extends AbstractPageObject {
    private static By moduleTitle, searchInp, clientsDataTable, page4Href, widgetContent, cellDataSpan;
    private static BrowserMobProxy proxyEuroNews = new BrowserMobProxyServer();
    private static String sPathToHar, sHarFileName;
    private static final long DEFAULT_PAUSE = 2000;

    public EuroNews(WebDriver driver, BrowserMobProxy proxy) {
        super(driver);
        this.proxyEuroNews = proxy;

        moduleTitle = By.xpath(propAPI.getProperty("h1_title"));
        searchInp = By.cssSelector(propAPI.getProperty("inp_Search"));
        clientsDataTable = By.cssSelector(propAPI.getProperty("table_ClientsData"));
        page4Href = By.xpath(propAPI.getProperty("href_Page4"));
        widgetContent = By.xpath(propAPI.getProperty("content_Widget"));
        cellDataSpan = By.xpath(propAPI.getProperty("span_CellData"));

        sPathToHar = System.getProperty("user.dir") + propAPI.getProperty("dataPath_EuroNewsHar");
        sHarFileName = "euroNews.har";

        proxyEuroNews.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS);
        proxyEuroNews.newHar("euroNews");
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getSearchInput() {
        return checkElementExists(searchInp);
    }

    public WebElement getClientsDataTable() {
        return checkElementExists(clientsDataTable);
    }

    public WebElement getPage4Href() {
        return checkElementExists(page4Href);
    }

    public WebElement getWidgetContent() {
        return checkElementExists(widgetContent);
    }

    public WebElement getCellDataSpan() {
        return checkElementExists(cellDataSpan);
    }

    public Har getHar() {
        Har har = proxyEuroNews.getHar();
        Functions.writeHarToFile(har, sPathToHar, sHarFileName);
        return har;
    }

    public de.sstoehr.harreader.model.Har readHarFromFile() {
        return Functions.readHarFromFile(sPathToHar, sHarFileName);
    }


    public ResponseDataObj getResponseData(JSONObject data) throws IOException, ParseException {
        return Functions.setResponseDataObj(proxyEuroNews, data.get("method").toString(), data.get("content_type").toString(), data);
    }

}
