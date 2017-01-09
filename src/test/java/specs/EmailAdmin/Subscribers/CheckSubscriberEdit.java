package specs.EmailAdmin.Subscribers;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.EmailAdmin.Subscribers.SubscriberEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-09.
 */

public class CheckSubscriberEdit extends AbstractSpec {

    private static By emailAdminMenuButton, subscribersMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SubscriberEdit subscriberEdit;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        subscribersMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Subscribers"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        subscriberEdit = new SubscriberEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(emailAdminMenuButton, subscribersMenuItem, addNewLink);
    }

    @Test
    public void checkSubscriberEdit() throws Exception {
        final String expectedTitle = "Subscriber Edit";

        Assert.assertNotNull(subscriberEdit.getUrl());
        Assert.assertEquals(subscriberEdit.getTitle(), expectedTitle, "Actual Subscriber Edit page Title doesn't match to expected");

        Assert.assertNotNull(subscriberEdit.getEmailAddressInput(), "Email Address input doesn't exist");

        Assert.assertNotNull(subscriberEdit.getFirstNameInput(), "First Name field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getLastNameInput(), "Last Name field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getCompanyInput(), "Company field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getTitleInput(), "Title field doesn't exist");

        Assert.assertTrue(subscriberEdit.getActiveValidChkSet(), "Checkbox Active/Validated elements don't exist");

        Assert.assertNotNull(subscriberEdit.getAddress1Input(), "Address line 1 field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getAddress2Input(), "Address line 2 field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getCityInput(), "City field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getProvinceInput(), "Province field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getPostalCodeInput(), "Postal Code field doesn't exist");

        Assert.assertNotNull(subscriberEdit.getCountrySelect(), "Country select doesn't exist");
        Assert.assertNotNull(subscriberEdit.getRegionInput(), "Region field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getTelephoneNoInput(), "Telephone No field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getFaxNoInput(), "Fax No field doesn't exist");
        Assert.assertNotNull(subscriberEdit.getHeardOfFromSelect(), "Heard Of From field doesn't exist");

        Assert.assertNotNull(subscriberEdit.getNotesTextarea(), "Notes textarea doesn't exist");

        Assert.assertNotNull(subscriberEdit.getMailingListsChk(), "Mailing Lists checkbox doesn't exist");

        Assert.assertNotNull(subscriberEdit.getCategoriesChk(), "Categories checkbox doesn't exist");

        Assert.assertNotNull(subscriberEdit.getSaveButton(), "Save button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
