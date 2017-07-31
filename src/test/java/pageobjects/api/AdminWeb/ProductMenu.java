package pageobjects.api.AdminWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-25.
 */

public class ProductMenu extends AbstractPageObject {
    private static By productDown, productWrapper, productWebSpan, productDesktopSpan, productSurveillanceSpan;
    private static By headerWeb;
    private static String sWeb, sDesktop, sSurveillance;
    private static final long DEFAULT_PAUSE = 1000;

    public ProductMenu(WebDriver driver) {
        super(driver);

        productDown = By.cssSelector(propAPI.getProperty("dropdown_Product"));
        productWrapper = By.cssSelector(propAPI.getProperty("wrapper_Product"));
        productWebSpan = By.xpath(propAPI.getProperty("span_ProductWeb"));
        productDesktopSpan = By.xpath(propAPI.getProperty("span_ProductDesktop"));
        productSurveillanceSpan = By.xpath(propAPI.getProperty("span_ProductSurveillance"));
        headerWeb = By.xpath(propAPI.getProperty("headerWeb"));

        sWeb = propAPI.getProperty("productItemWeb");
        sDesktop = propAPI.getProperty("productItemDesktop");
        sSurveillance = propAPI.getProperty("productItemSurveillance");
    }

    public String changeMenuItem (String sMenuItem) throws InterruptedException {
        waitForElement(productDown);
        findElement(productDown).click();

        //Thread.sleep(DEFAULT_PAUSE);

        // Choose the product
        waitForElement(productWrapper);
        WebElement elProductWrapper = findElement(productWrapper);
        elProductWrapper.findElement(By.xpath("//span[contains(text(), '"+sMenuItem+"')]")).click();

        return findElement(headerWeb).getCssValue("background-color").toString();
    }

    public String changeMenuItemJS (String sMenuItem) throws InterruptedException {
        waitForElement(productDown);

        return findElement(headerWeb).getCssValue("background-color").toString();
    }

}
