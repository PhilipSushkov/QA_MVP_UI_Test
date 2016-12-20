package specs.SiteAdmin.AliasList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.AliasList.AliasEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckAliasEdit extends AbstractSpec {

    private static By siteAdminMenuButton, aliasListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AliasEdit aliasEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        aliasListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_AliasList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        aliasEdit = new AliasEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, aliasListMenuItem, userAddNewLink);
    }

    @Test
    public void checkAliasEdit() throws Exception {
        final String expectedTitle = "Alias Edit";

        Assert.assertNotNull(aliasEdit.getUrl());
        Assert.assertEquals(aliasEdit.getTitle(), expectedTitle, "Actual Alias Edit page Title doesn't match to expected");

        Assert.assertNotNull(aliasEdit.getAliasTypeSelect(), "Alias Type select doesn't exist");
        Assert.assertNotNull(aliasEdit.getRedirectTypeSelect(), "Redirect Type select doesn't exist");
        Assert.assertNotNull(aliasEdit.getAliasNameInput(), "Alias Name field doesn't exist");
        Assert.assertNotNull(aliasEdit.getTargetLanguageSelect(), "Target Language select doesn't exist");
        Assert.assertNotNull(aliasEdit.getTargetPageSelect(), "Target Page select doesn't exist");
        Assert.assertNotNull(aliasEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
