package pageobjects.api.AdminWeb.EuroNews;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.ApiAbstractSpec.propAPI;

import org.json.simple.parser.ParseException;
import pageobjects.api.AdminWeb.ResponseDataObj;
import util.Functions;

import java.io.*;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class EuroNews extends AbstractPageObject {
    private static By moduleTitle, searchInp, clientsDataTable, page4Href, widgetContent, cellDataSpan, spinnerDiv;
    private static BrowserMobProxy proxyEuroNews = new BrowserMobProxyServer();
    private static String sPathToHar, sHarFileName, sPathToFile, sPathToSchema;
    private static String searchWord;
    //private static final long DEFAULT_PAUSE = 2000;

    public EuroNews(WebDriver driver, BrowserMobProxy proxy) {
        super(driver);
        this.proxyEuroNews = proxy;

        moduleTitle = By.xpath(propAPI.getProperty("h1_title"));
        searchInp = By.cssSelector(propAPI.getProperty("inp_Search"));
        clientsDataTable = By.cssSelector(propAPI.getProperty("table_ClientsData"));
        page4Href = By.xpath(propAPI.getProperty("href_Page4"));
        widgetContent = By.xpath(propAPI.getProperty("content_Widget"));
        cellDataSpan = By.xpath(propAPI.getProperty("span_CellData"));
        spinnerDiv = By.xpath(propAPI.getProperty("div_spinner"));

        searchWord = propAPI.getProperty("searchWord");

        sPathToHar = System.getProperty("user.dir") + propAPI.getProperty("dataPath_EuroNewsHar");
        sPathToFile = System.getProperty("user.dir") + propAPI.getProperty("dataPath_EuroNewsClientList");
        sHarFileName = "euroNews.har";

        sPathToSchema = System.getProperty("user.dir") + propAPI.getProperty("dataPath_EuroNewsSchema");

        proxyEuroNews.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS);
        proxyEuroNews.newHar("euroNews");
    }


    public String getTitle() {
        waitForAnyElementToAppear(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getSearchInput() {
        return checkElementExists(searchInp);
    }

    public WebElement getClientsDataTable() {
        return checkElementExists(clientsDataTable);
    }

    public WebElement getPage4Href() {
        waitForLoadingScreen(spinnerDiv);
        return checkElementExists(page4Href);
    }

    public WebElement getWidgetContent() {
        waitForLoadingScreen(spinnerDiv);
        return checkElementExists(widgetContent);
    }

    public WebElement getCellDataSpan() {
        waitForLoadingScreen(spinnerDiv);
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
        ResponseDataObj responseDataObj = Functions.setResponseDataObj(proxyEuroNews, data, sPathToFile);
        return responseDataObj;
    }

    public boolean getSchemaValidation(JSONObject data) throws IOException, ParseException {
        String sSchemaFileName = "schema_" + data.get("api_request_name").toString() + ".json";
        String sResultFileName = "result_" + data.get("api_request_name").toString() + ".json";
        return Functions.getSchemaValidation(sPathToSchema, sPathToFile, sSchemaFileName, sResultFileName);
    }

    public void clickPage4Href() {
        findElement(page4Href).click();
        waitForLoadingScreen(spinnerDiv);
        System.out.println("The next request is done");
    }

    public void clickSearch() {
        //findElement(searchInp).sendKeys(searchWord);

        waitForElement(searchInp);
        WebElement element = findElement(searchInp);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, "value", searchWord);
        element.sendKeys(Keys.RETURN);

        waitForLoadingScreen(spinnerDiv);
        System.out.println("The search request is done");
    }

    public void removeSearchWord() {
        waitForElement(searchInp);
        WebElement element = findElement(searchInp);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, "value", "");
        element.sendKeys(Keys.RETURN);

        waitForLoadingScreen(spinnerDiv);
        System.out.println("The search request is removed");
    }
}
