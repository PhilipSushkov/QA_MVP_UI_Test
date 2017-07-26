package pageobjects.api.AdminWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.EmailAdmin.SystemMessages.SystemMessageEdit;

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

        productDown = By.xpath(propAPI.getProperty("dropdown_Product"));
        productWrapper = By.xpath(propAPI.getProperty("wrapper_Product"));
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

        waitForElement(productWrapper);
        WebElement elProductWrapper = findElement(productWrapper);

        if (sMenuItem.equals(sWeb)) {
            elProductWrapper.findElement(productWebSpan).click();
        } else
            if (sMenuItem.equals(sDesktop)) {
                elProductWrapper.findElement(productDesktopSpan).click();
            } else
                if (sMenuItem.equals(sSurveillance)) {
                    elProductWrapper.findElement(productSurveillanceSpan).click();
                }
        return findElement(headerWeb).getCssValue("background-color").toString();
    }
}
