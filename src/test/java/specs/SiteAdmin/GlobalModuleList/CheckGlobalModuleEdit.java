package specs.SiteAdmin.GlobalModuleList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.GlobalModuleList.GlobalModuleEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */
public class CheckGlobalModuleEdit extends AbstractSpec {

    private static By siteAdminMenuButton, globalModuleListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlobalModuleEdit globalModuleEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        globalModuleListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_GlobalModuleList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        globalModuleEdit = new GlobalModuleEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, globalModuleListMenuItem, userAddNewLink);
    }

    @Test
    public void checkGlobalModuleEdit() throws Exception {
        final String expectedTitle = "Global Module Edit";

        Assert.assertNotNull(globalModuleEdit.getUrl());
        Assert.assertEquals(globalModuleEdit.getTitle(), expectedTitle, "Actual Global Module Edit page Title doesn't match to expected");

        Assert.assertNotNull(globalModuleEdit.getModuleTitleInput(), "Module Title field doesn't exist");
        Assert.assertNotNull(globalModuleEdit.getModuleDefinitionSelect(), "Module Definition Select doesn't exist");
        Assert.assertNotNull(globalModuleEdit.getModuleTypeSelect(), "Module Type Select doesn't exist");
        Assert.assertNotNull(globalModuleEdit.getRegionNameSelect(), "Region Name Select doesn't exist");
        Assert.assertNotNull(globalModuleEdit.getSaveAndSubmitButton(), "Save And Submit Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
