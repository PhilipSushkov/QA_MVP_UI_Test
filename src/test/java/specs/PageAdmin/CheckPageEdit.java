package specs.PageAdmin;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.PageAdmin.PageEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-12.
 */

public class CheckPageEdit extends AbstractSpec {

    private static By pageAdminMenuButton, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageEdit pageEdit;

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIPageAdmin.getProperty("btnMenu_PageAdmin"));
        userAddNewLink = By.xpath(propUIPageAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageEdit = new PageEdit(driver);

        loginPage.loginUser();

        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
        dashboard.openPageFromCommonTasks(userAddNewLink);
    }

    @Test
    public void checkPageEdit() throws Exception {
        final String expectedTitle = "Page Edit";

        Assert.assertNotNull(pageEdit.getUrl());
        Assert.assertEquals(pageEdit.getTitle(), expectedTitle, "Actual Page Edit page Title doesn't match to expected");

        Assert.assertNotNull(pageEdit.getSectionWrapBtn(), "Section wrap button doesn't exist");
        Assert.assertNotNull(pageEdit.getSecurityWrapBtn(), "Security wrap button doesn't exist");

        Assert.assertNotNull(pageEdit.getSectionTitleInput(), "Section Title field doesn't exist");
        Assert.assertNotNull(pageEdit.getYourPageUrlLabel(), "Your page URL label doesn't exist");
        Assert.assertNotNull(pageEdit.getChangeUrlLink(), "Change URL link doesn't exist");
        Assert.assertNotNull(pageEdit.getPageTemplateSelect(), "Page Template select doesn't exist");
        Assert.assertNotNull(pageEdit.getParentPageSelect(), "Parent Page select doesn't exist");

        Assert.assertTrue(pageEdit.getPageTypeRdSet(), "Page Type radio elements don't exist");
        Assert.assertTrue(pageEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(pageEdit.getTemplateNameInput(), "Template Name field doesn't exist");
        Assert.assertNotNull(pageEdit.getSaveAsTemplateBtn(), "Save Template button doesn't exist");

        Assert.assertNotNull(pageEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
