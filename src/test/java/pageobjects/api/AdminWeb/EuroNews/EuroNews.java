package pageobjects.api.AdminWeb.EuroNews;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class EuroNews extends AbstractPageObject {
    private static By moduleTitle, searchInp, clientsDataTable, page4Href, widgetContent, cellDataSpan;
    private static BrowserMobProxy proxyEuroNews = new BrowserMobProxyServer();
    private static Har har;
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

        proxyEuroNews.enableHarCaptureTypes(CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS);
        proxyEuroNews.newHar("euroNews");
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getSearchInput() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(searchInp)));
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
            wait.until(ExpectedConditions.visibilityOf(findElement(clientsDataTable)));
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
            wait.until(ExpectedConditions.visibilityOf(findElement(page4Href)));
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
            wait.until(ExpectedConditions.visibilityOf(findElement(widgetContent)));
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
            wait.until(ExpectedConditions.visibilityOf(findElement(cellDataSpan)));
            element = findElement(cellDataSpan);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public Har getHar() {
        // get the HAR data
        har = proxyEuroNews.getHar();
        return har;
    }

}
