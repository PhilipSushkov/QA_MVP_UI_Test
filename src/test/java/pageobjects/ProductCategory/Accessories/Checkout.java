package pageobjects.ProductCategory.Accessories;

import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;
import util.Log;

import static specs.AbstractSpec.propUIProdCategory;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/


public class Checkout extends AbstractPageObject {
    private static By continueBtn, emailInp, firstNameInp;
    private static String sItemCheckout = propUIProdCategory.getProperty("href_ItemCheckout");
    private static String sItemQuantity = propUIProdCategory.getProperty("inp_ItemQuantity");

    public Checkout(WebDriver driver) {
        super(driver);

        continueBtn = By.xpath(propUIProdCategory.getProperty("href_Continue"));
        emailInp = By.xpath(propUIProdCategory.getProperty("inp_Email"));
        firstNameInp = By.xpath(propUIProdCategory.getProperty("inp_FirstName"));
    }

    public String getTitle() {
        try {
            waitForElement(continueBtn);
            return driver.getTitle();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Get Checkout Page Title: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Get Checkout Page Title: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Get Checkout Page Title: TimeoutException occurred");
        }

        return null;
    }

    public String getItemName(JSONObject data) {
        String sItem = data.get("item").toString();

        try {
            By itemCheckoutHref = By.xpath(String.format(sItemCheckout, sItem));
            waitForElement(itemCheckoutHref);
            return findElement(itemCheckoutHref).getText();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Name: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Name: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Name: TimeoutException occurred");
        }

        return null;
    }

    public long getItemQuantity(JSONObject data) {
        String sItem = data.get("item").toString();

        try {
            By itemQuantityInp = By.xpath(String.format(sItemQuantity, sItem));
            waitForElement(itemQuantityInp);
            return Long.parseLong(findElement(itemQuantityInp).getAttribute("value"));
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Quantity: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Quantity: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Quantity: TimeoutException occurred");
        }

        return 0;
    }

    public void clickContinue() {
        try {
            findElement(continueBtn).click();
            Log.info("Continue button has been clicked successfully");
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Checkout. Continue button: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Checkout. Continue button: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Checkout. Continue button: TimeoutException occurred");
        }
    }

    public void fillUpData(JSONObject contacts) {
        try {
            // Entering all data needed for e-mail and billing/contact details
            // Billing E-mail field
            waitForElementToBeClickable(emailInp);
            WebElement emailElement = findElement(emailInp);
            emailElement.clear();
            emailElement.sendKeys(contacts.get("email").toString());

            // First Name field
            waitForElementToBeClickable(firstNameInp);
            WebElement firstNameElement = findElement(firstNameInp);
            firstNameElement.clear();
            firstNameElement.sendKeys(contacts.get("firstName").toString());
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Checkout. Fill Up data: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Checkout. Fill Up data: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Checkout. Fill Up data: TimeoutException occurred");
        }


        // Last Name field
        By lastNameInp = By.xpath("//input[@title='billinglastname']");
        waitForElementToBeClickable(lastNameInp);
        WebElement lastNameElement = findElement(lastNameInp);
        lastNameElement.clear();
        lastNameElement.sendKeys("Lima");

        // Address field
        By addressInp = By.xpath("//textarea[@title='billingaddress']");
        waitForElementToBeClickable(addressInp);
        WebElement addressElement = findElement(addressInp);
        addressElement.clear();
        addressElement.sendKeys("1512 Rua Sizenando Nabuco");

        // City field
        By cityInp = By.xpath("//input[@title='billingcity']");
        waitForElementToBeClickable(cityInp);
        WebElement cityElement = findElement(cityInp);
        cityElement.clear();
        cityElement.sendKeys("São Paulo");

        // State/Province field
        By stateProvinceInp = By.xpath("//input[@title='billingstate']");
        waitForElementToBeClickable(stateProvinceInp);
        WebElement stateProvinceElement = findElement(stateProvinceInp);
        stateProvinceElement.clear();
        stateProvinceElement.sendKeys("SP");

        // Select Country value
        By countrySel = By.xpath("//select[@title='billingcountry']");
        Select countryElement = new Select(findElement(countrySel));
        countryElement.selectByVisibleText("Brazil");

        // Postal Code field
        By postCodeInp = By.xpath("//input[@title='billingpostcode']");
        waitForElementToBeClickable(postCodeInp);
        WebElement postCodeElement = findElement(postCodeInp);
        postCodeElement.clear();
        postCodeElement.sendKeys("03570-060");

        // Phone field
        By phoneInp = By.xpath("//input[@title='billingphone']");
        waitForElementToBeClickable(postCodeInp);
        WebElement phoneElement = findElement(phoneInp);
        phoneElement.clear();
        phoneElement.sendKeys("(11) 5885-4415");
    }

}
