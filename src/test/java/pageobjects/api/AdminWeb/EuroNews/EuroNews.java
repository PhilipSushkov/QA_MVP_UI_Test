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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pageobjects.api.AdminWeb.ResponseDataObj;
import util.Functions;

import java.io.IOException;
import java.util.List;

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


    public ResponseDataObj getResponseData(JSONObject data) {
        JSONParser parser;
        HttpClient client;
        String sReguestUrl;
        ResponseDataObj responseDataObj = new ResponseDataObj();

        String sMethod = data.get("method").toString();
        String sContentType = data.get("content_type").toString();
        String sUrlData = Functions.getUrlFromData(data);

        for (HarEntry entry : proxyEuroNews.getHar().getLog().getEntries()) {
            HarRequest request = entry.getRequest();
            HarResponse response = entry.getResponse();

            List<HarNameValuePair> harListResponse = response.getHeaders();
            if (request.getUrl().equals(sUrlData)
                    && request.getMethod().equals(sMethod)
                    && harListResponse.get(0).getValue().contains(sContentType)) {
                sReguestUrl = request.getUrl();

                parser = new JSONParser();
                client = HttpClientBuilder.create().build();

                // Send Http Api request for chosen upper URL
                HttpGet get = new HttpGet(sReguestUrl);
                List<HarNameValuePair> params = request.getHeaders();
                for (HarNameValuePair param : params) {
                    get.setHeader(param.getName(), param.getValue());
                }

                // Analyze Http Response and extract JSON-data
                try {
                    long start = System.currentTimeMillis();
                    HttpResponse httpResponse = client.execute(get);
                    long end = System.currentTimeMillis();
                    responseDataObj.setResponseTime(end-start);
                    System.out.println("Response Time of "+data.get("api_request_name").toString()+" is: " + responseDataObj.getResponseTime() + " ms");

                    responseDataObj.setResponseCode(httpResponse.getStatusLine().getStatusCode());
                    System.out.println("Response Code of "+data.get("api_request_name").toString()+" is: " + responseDataObj.getResponseCode());

                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        responseDataObj.setJsonResponse((JSONObject) parser.parse(EntityUtils.toString(httpEntity)));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        //Functions.writeHarToFile(har, sPathToHar, sHarFileName);

        return responseDataObj;
    }


}
