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

    public WebElement getSearchInput() throws InterruptedException {
        WebElement element = null;

        try {
            waitForElement(searchInp);
            element = findElement(searchInp);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getClientsDataTable() {
        WebElement element = null;

        try {
            waitForElement(clientsDataTable);
            element = findElement(clientsDataTable);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getPage4Href() {
        WebElement element = null;

        try {
            waitForElement(page4Href);
            element = findElement(page4Href);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getWidgetContent() {
        WebElement element = null;

        try {
            waitForElement(widgetContent);
            element = findElement(widgetContent);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getCellDataSpan() {
        WebElement element = null;

        try {
            waitForElement(cellDataSpan);
            element = findElement(cellDataSpan);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
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

        //String sDomain = data.get("domain").toString();
        String sMethod = data.get("method").toString();
        String sContentType = data.get("content_type").toString();
        //String sApiRequestName =data.get("api_request_name").toString();
        String sUrlData = Functions.getUrlFromData(data);
        //System.out.println(sDomain + ": " + sMethod);

        for (HarEntry entry : proxyEuroNews.getHar().getLog().getEntries()) {
            HarRequest request = entry.getRequest();
            HarResponse response = entry.getResponse();

            List<HarNameValuePair> harListResponse = response.getHeaders();
            if (request.getUrl().equals(sUrlData) && request.getMethod().equals(sMethod) && harListResponse.get(0).getValue().contains(sContentType)) {
                sReguestUrl = request.getUrl();
                //System.out.println(request.getUrl() + " " + response.getStatus());

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
                    System.out.println("Response time = " + (end-start) + " millis");

                    HttpEntity httpEntity = httpResponse.getEntity();
                    if (httpEntity != null) {
                        //String responseBody = EntityUtils.toString(httpEntity);
                        //jsonResponse = (JSONObject) parser.parse(responseBody);

                        responseDataObj.setJsonResponse((JSONObject) parser.parse(EntityUtils.toString(httpEntity)));
                        //System.out.println(responseDataObj.getJsonResponse().toJSONString());

                        /*
                        // Write JSON-data to the file
                        FileWriter file = new FileWriter(sApiRequestName+".json");
                        file.write(jsonResponse.toJSONString().replace("\\", ""));
                        file.flush();
                        */
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
