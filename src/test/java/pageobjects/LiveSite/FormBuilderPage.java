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

    private final By firstNameField, lastNameField, emailField, companyField, addressField, cityField;
    private final By countryField, phoneField, commentsField, submitButton, formBuilderHeader, headerMessage;

    public FormBuilderPage(WebDriver driver) {
        super(driver);
        firstNameField = By.xpath(propUIPublicSite.getProperty("firstNameField"));
        lastNameField = By.xpath(propUIPublicSite.getProperty("lastNameField"));
        emailField = By.xpath(propUIPublicSite.getProperty("emailField"));
        companyField = By.xpath(propUIPublicSite.getProperty("companyField"));
        addressField = By.xpath(propUIPublicSite.getProperty("addressField"));
        cityField = By.xpath(propUIPublicSite.getProperty("cityField"));
        countryField = By.xpath(propUIPublicSite.getProperty("countryField"));
        phoneField = By.xpath(propUIPublicSite.getProperty("phoneField"));
        commentsField = By.xpath(propUIPublicSite.getProperty("commentsField"));

        submitButton = By.xpath(propUIPublicSite.getProperty("submitButton"));

        formBuilderHeader = By.xpath(propUIPublicSite.getProperty("formBuilderHeader"));
        headerMessage = By.xpath(propUIPublicSite.getProperty("headerMessage"));
    }

    public void completeForm(JSONObject data, String randLastName) {

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

        findElement(submitButton).click();
    }

    public boolean formBuilderPageDisplayed() { return findElement(formBuilderHeader).isDisplayed(); }

    public boolean correctMessageDisplayed(String expected) {
        if (!expected.equals("none")) {
            String text = findElement(headerMessage).getText();
            return text.contains(expected);
        } else return true;
    }

}
