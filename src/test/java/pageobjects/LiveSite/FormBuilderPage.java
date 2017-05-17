package pageobjects.LiveSite;

import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import org.openqa.selenium.By;

import static specs.AbstractSpec.propUIPublicSite;

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

    public void enterFields(String first, String last, String email, String company, String address, String city, String country, String phone, String comments) {
        findElement(firstNameField).clear();
        findElement(firstNameField).sendKeys(first);
        findElement(lastNameField).clear();
        findElement(lastNameField).sendKeys(last);
        findElement(emailField).clear();
        findElement(emailField).sendKeys(email);
        findElement(companyField).clear();
        findElement(companyField).sendKeys(company);
        findElement(addressField).clear();
        findElement(addressField).sendKeys(address);
        findElement(cityField).clear();
        findElement(cityField).sendKeys(city);
        findElement(countryField).sendKeys(country);
        findElement(phoneField).clear();
        findElement(phoneField).sendKeys(phone);
        findElement(commentsField).clear();
        findElement(commentsField).sendKeys(comments);
    }

    public void submitForm() { findElement(submitButton).click(); }

    public boolean formBuilderPageDisplayed() { return findElement(formBuilderHeader).isDisplayed(); }

    public boolean successDisplayed() {
            String text = findElement(headerMessage).getText();
            return text.contains(propUIPublicSite.getProperty("successMessage"));
    }

}
