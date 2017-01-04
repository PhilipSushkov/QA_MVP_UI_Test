package specs.ContentAdmin.Glossary;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Glossary.GlossaryEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckGlossaryEdit extends AbstractSpec {

    private static By contentAdminMenuButton, glossaryListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlossaryEdit glossaryEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        glossaryListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_Glossary"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        glossaryEdit = new GlossaryEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, glossaryListMenuItem, addNewLink);
    }

    @Test
    public void checkGlossaryEdit() throws Exception {
        final String expectedTitle = "Glossary Edit";

        Assert.assertNotNull(glossaryEdit.getUrl());
        Assert.assertEquals(glossaryEdit.getTitle(), expectedTitle, "Actual Glossary Edit Edit page Title doesn't match to expected");

        Assert.assertNotNull(glossaryEdit.getTitleInput(), "Title field doesn't exist");
        Assert.assertNotNull(glossaryEdit.getDescriptionFrame(), "Description frame doesn't exist");
        Assert.assertNotNull(glossaryEdit.getActiveCheckbox(), "Active checkbox doesn't exist");

        Assert.assertNotNull(glossaryEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
