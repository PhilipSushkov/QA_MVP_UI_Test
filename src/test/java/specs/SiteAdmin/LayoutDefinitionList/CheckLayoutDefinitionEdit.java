package specs.SiteAdmin.LayoutDefinitionList;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.LayoutDefinitionList.LayoutDefinitionEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckLayoutDefinitionEdit extends AbstractSpec {

    private static By siteAdminMenuButton, layoutDefinitionListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LayoutDefinitionEdit layoutDefinitionEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        layoutDefinitionListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LayoutDefinitionList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        layoutDefinitionEdit = new LayoutDefinitionEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, layoutDefinitionListMenuItem, userAddNewLink);
    }

    @Test
    public void checkLayoutDefinitionEdit() throws Exception {
        final String expectedTitle = "Layout Definition Edit";

        Assert.assertNotNull(layoutDefinitionEdit.getUrl());
        Assert.assertEquals(layoutDefinitionEdit.getTitle(), expectedTitle, "Layout Definition Edit page Title doesn't match to expected");

        Assert.assertNotNull(layoutDefinitionEdit.getFriendlyNameInput(), "Friendly Name field doesn't exist");
        Assert.assertNotNull(layoutDefinitionEdit.getDescriptionInput(), "Description field doesn't exist");
        Assert.assertNotNull(layoutDefinitionEdit.getDefaultLayoutSelect(), "Default Layout Select doesn't exist");
        Assert.assertNotNull(layoutDefinitionEdit.getActiveCheckbox(), "Active Checkbox doesn't exist");
        Assert.assertNotNull(layoutDefinitionEdit.getSaveAndSubmitButton(), "Save And Submit Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
