package specs.SiteAdmin.LookupList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.LookupList.LookupEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckLookupEdit extends AbstractSpec {

    private static By siteAdminMenuButton, lookupListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LookupEdit lookupEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        lookupListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LookupList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        lookupEdit = new LookupEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, lookupListMenuItem, userAddNewLink);
    }

    @Test
    public void checkLookupEdit() throws Exception {
        final String expectedTitle = "Lookup Edit";

        Assert.assertNotNull(lookupEdit.getUrl());
        Assert.assertEquals(lookupEdit.getTitle(), expectedTitle, "Actual Lookup Edit page Title doesn't match to expected");

        Assert.assertNotNull(lookupEdit.getLookupTypeInput(), "Lookup Type field doesn't exist");
        Assert.assertNotNull(lookupEdit.getLookupTextInput(), "Lookup Text field doesn't exist");
        Assert.assertNotNull(lookupEdit.getLookupValueInput(), "Lookup Value field doesn't exist");
        Assert.assertNotNull(lookupEdit.getAdditionalInfoInput(), "Additional Info field doesn't exist");
        Assert.assertNotNull(lookupEdit.getActiveCheckbox(), "Active Checkbox doesn't exist");
        Assert.assertNotNull(lookupEdit.getSaveAndSubmitButton(), "Save And Submit Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
