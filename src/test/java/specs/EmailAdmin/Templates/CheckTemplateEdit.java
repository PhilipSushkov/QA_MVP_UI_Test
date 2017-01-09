package specs.EmailAdmin.Templates;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.EmailAdmin.Templates.TemplateEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-09.
 */

public class CheckTemplateEdit extends AbstractSpec {

    private static By emailAdminMenuButton, templatesMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static TemplateEdit templateEdit;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        templatesMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Templates"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        templateEdit = new TemplateEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(emailAdminMenuButton, templatesMenuItem, addNewLink);
    }

    @Test
    public void checkTemplateEdit() throws Exception {
        final String expectedTitle = "Template Edit";

        Assert.assertNotNull(templateEdit.getUrl());
        Assert.assertEquals(templateEdit.getTitle(), expectedTitle, "Actual Template Edit page Title doesn't match to expected");

        Assert.assertNotNull(templateEdit.getTemplateNameInput(), "Template Name field doesn't exist");
        Assert.assertNotNull(templateEdit.getSubjectInput(), "Subject field doesn't exist");
        Assert.assertNotNull(templateEdit.getFromInput(), "From input doesn't exist");

        Assert.assertTrue(templateEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(templateEdit.getBodyFrame(), "Body frame doesn't exist");
        Assert.assertNotNull(templateEdit.getTestEmailInput(), "Test Email field doesn't exist");
        Assert.assertNotNull(templateEdit.getEntityTypeSelect(), "Entity Type select doesn't exist");
        Assert.assertNotNull(templateEdit.getSendButton(), "Send button doesn't exist");

        Assert.assertNotNull(templateEdit.getSaveButton(), "Save button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
