package specs.EmailAdmin.SystemMessages;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.EmailAdmin.SystemMessages.SystemMessageEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-09.
 */

public class CheckSystemMessageEdit extends AbstractSpec {

    private static By emailAdminMenuButton, systemMessagesMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SystemMessageEdit systemMessageEdit;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        systemMessagesMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_SystemMessages"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        systemMessageEdit = new SystemMessageEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(emailAdminMenuButton, systemMessagesMenuItem, addNewLink);
    }

    @Test
    public void checkSystemMessageEdit() throws Exception {
        final String expectedTitle = "System Message Edit";

        Assert.assertNotNull(systemMessageEdit.getUrl());
        Assert.assertEquals(systemMessageEdit.getTitle(), expectedTitle, "Actual System Message Edit page Title doesn't match to expected");

        Assert.assertNotNull(systemMessageEdit.getSystemMessageNameInput(), "System Message Name field doesn't exist");
        Assert.assertNotNull(systemMessageEdit.getDescriptionInput(), "Description field doesn't exist");
        Assert.assertNotNull(systemMessageEdit.getFromInput(), "From field doesn't exist");
        Assert.assertNotNull(systemMessageEdit.getSubjectInput(), "Subject field doesn't exist");

        Assert.assertTrue(systemMessageEdit.getChkBoxSet(), "Checkbox elements don't exist");
        Assert.assertTrue(systemMessageEdit.getTypeRdSet(), "Type Radio elements don't exist");

        Assert.assertNotNull(systemMessageEdit.getSubscriptionSelect(), "Subscription select doesn't exist");
        Assert.assertNotNull(systemMessageEdit.getUrlLabel(), "Url label doesn't exist");
        Assert.assertNotNull(systemMessageEdit.getBodyFrame(), "Body frame doesn't exist");

        Assert.assertNotNull(systemMessageEdit.getSaveButton(), "Save button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
