package specs.EmailAdmin.ManageList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.EmailAdmin.ManageList.MailingListsEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class CheckMailingListsEdit extends AbstractSpec {

    private static By emailAdminMenuButton, manageListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MailingListsEdit mailingListsEdit;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        manageListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_ManageList"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mailingListsEdit = new MailingListsEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(emailAdminMenuButton, manageListMenuItem, addNewLink);
    }

    @Test
    public void checkPersonEdit() throws Exception {
        final String expectedTitle = "Mailing Lists Edit";

        Assert.assertNotNull(mailingListsEdit.getUrl());
        Assert.assertEquals(mailingListsEdit.getTitle(), expectedTitle, "Actual Mailing Lists Edit page Title doesn't match to expected");

        Assert.assertNotNull(mailingListsEdit.getMailingListNameInput(), "Mailing List Name input doesn't exist");
        Assert.assertNotNull(mailingListsEdit.getDescriptionTextarea(), "Description textarea doesn't exist");

        Assert.assertTrue(mailingListsEdit.getPublicRadioSet(), "Public radio elements don't exist");

        Assert.assertNotNull(mailingListsEdit.getActivationEmailSelect(), "Activation Email select doesn't exist");
        Assert.assertNotNull(mailingListsEdit.getUnsubscribeEmailSelect(), "Unsubscribe Email select doesn't exist");

        Assert.assertTrue(mailingListsEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(mailingListsEdit.getSaveButton(), "Save button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
