package specs.SiteAdmin.ModuleDefinitionList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.ModuleDefinitionList.ModuleDefinitionEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckModuleDefinitionEdit extends AbstractSpec {

    private static By siteAdminMenuButton, moduleDefinitionListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ModuleDefinitionEdit moduleDefinitionEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        moduleDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ModuleDefinitionList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        moduleDefinitionEdit = new ModuleDefinitionEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, moduleDefinitionListMenuItem, userAddNewLink);
    }

    @Test
    public void checkModuleDefinitionEdit() throws Exception {
        final String expectedTitle = "Module Definition Edit";

        Assert.assertNotNull(moduleDefinitionEdit.getUrl());
        Assert.assertEquals(moduleDefinitionEdit.getTitle(), expectedTitle, "Actual Module Definition Edit page Title doesn't match to expected");

        Assert.assertNotNull(moduleDefinitionEdit.getFriendlyNameInput(), "Friendly Name field doesn't exist");
        Assert.assertNotNull(moduleDefinitionEdit.getQualifiedPathInput(), "Qualified Path Input doesn't exist");
        Assert.assertNotNull(moduleDefinitionEdit.getLinkToEditPageSelect(), "Link To Edit Page Select doesn't exist");
        Assert.assertNotNull(moduleDefinitionEdit.getLinkToListPageSelect(), "Link To List Page Select doesn't exist");
        Assert.assertNotNull(moduleDefinitionEdit.getLinkToAdminPageSelect(), "Link To Admin Page Select doesn't exist");
        Assert.assertNotNull(moduleDefinitionEdit.getActiveCheckbox(), "Active Checkbox doesn't exist");
        Assert.assertNotNull(moduleDefinitionEdit.getSaveAndSubmitButton(), "Save And Submit Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
