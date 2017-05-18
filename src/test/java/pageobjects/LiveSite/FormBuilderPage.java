package pageobjects.LiveSite;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import org.openqa.selenium.By;

import static specs.AbstractSpec.propUIPublicSite;
import static util.Functions.cleanTextFields;

/**
 * Created by zacharyk on 2017-05-16.
 */

public class FormBuilderPage extends AbstractPageObject {

    private final By firstNameField = By.xpath(propUIPublicSite.getProperty("firstNameField"));
    private final By lastNameField = By.xpath(propUIPublicSite.getProperty("lastNameField"));
    private final By emailField = By.xpath(propUIPublicSite.getProperty("emailField"));
    private final By companyField = By.xpath(propUIPublicSite.getProperty("companyField"));
    private final By addressField = By.xpath(propUIPublicSite.getProperty("addressField"));
    private final By cityField = By.xpath(propUIPublicSite.getProperty("cityField"));
    private final By countryField = By.xpath(propUIPublicSite.getProperty("countryField"));
    private final By phoneField = By.xpath(propUIPublicSite.getProperty("phoneField"));
    private final By commentsField = By.xpath(propUIPublicSite.getProperty("commentsField"));

    private final By submitButton = By.xpath(propUIPublicSite.getProperty("submitButton"));

    private final By formBuilderHeader = By.xpath(propUIPublicSite.getProperty("formBuilderHeader"));
    private final By headerMessage = By.xpath(propUIPublicSite.getProperty("headerMessage"));



    public FormBuilderPage(WebDriver driver) { super(driver); }

    public void enterFields(JSONObject data, String randLastName) {

        java.util.List<WebElement> textFields =  findElements(By.xpath("//input[@type='text']"));

        cleanTextFields(textFields);

        findElement(firstNameField).sendKeys(data.get("first_name").toString());
        findElement(lastNameField).sendKeys(randLastName);
        findElement(emailField).sendKeys(data.get("email").toString());
        findElement(companyField).sendKeys(data.get("company").toString());
        findElement(addressField).sendKeys(data.get("address").toString());
        findElement(cityField).sendKeys(data.get("city").toString());
        findElement(countryField).sendKeys(data.get("country").toString());
        findElement(phoneField).sendKeys(data.get("phone").toString());
        findElement(commentsField).sendKeys(data.get("comments").toString());
    }

    public void submitForm() { findElement(submitButton).click(); }

    public boolean formBuilderPageDisplayed() { return findElement(formBuilderHeader).isDisplayed(); }

    public boolean correctMessageDisplayed(String expected) {
            String text = findElement(headerMessage).getText();
            return text.contains(expected);
    }

}
