package specs.SiteAdmin.CssFileList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.CssFileList.CssFileEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckCssFileEdit extends AbstractSpec {

    private static By siteAdminMenuButton, cssFileListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CssFileEdit cssFileEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        cssFileListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_CssFileList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        cssFileEdit = new CssFileEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, cssFileListMenuItem, userAddNewLink);
    }

    @Test
    public void checkCssFileEdit() throws Exception {
        final String expectedTitle = "Css File Edit";

        Assert.assertNotNull(cssFileEdit.getUrl());
        Assert.assertEquals(cssFileEdit.getTitle(), expectedTitle, "Actual Css File Edit page Title doesn't match to expected");

        Assert.assertNotNull(cssFileEdit.getCssNameInput(), "Css Name field doesn't exist");
        Assert.assertNotNull(cssFileEdit.getCssHighlightingCheckbox(), "Css Highlighting checkbox doesn't exist");
        Assert.assertNotNull(cssFileEdit.getCssBodyTextarea(), "Css Body textarea doesn't exist");
        Assert.assertNotNull(cssFileEdit.getActiveCheckbox(), "Active Checkbox doesn't exist");
        Assert.assertNotNull(cssFileEdit.getSaveAndSubmitButton(), "Save And Submit Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
