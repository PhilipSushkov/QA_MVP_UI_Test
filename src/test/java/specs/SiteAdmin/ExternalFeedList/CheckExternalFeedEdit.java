package specs.SiteAdmin.ExternalFeedList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.ExternalFeedList.ExternalFeedEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckExternalFeedEdit extends AbstractSpec {

    private static By siteAdminMenuButton, externalFeedListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ExternalFeedEdit externalFeedEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        externalFeedListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ExternalFeedList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        externalFeedEdit = new ExternalFeedEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, externalFeedListMenuItem, userAddNewLink);
    }

    @Test
    public void checkExternalFeedEdit() throws Exception {
        final String expectedTitle = "External Feed Edit";

        Assert.assertNotNull(externalFeedEdit.getUrl());
        Assert.assertEquals(externalFeedEdit.getTitle(), expectedTitle, "Actual External Feed Edit page Title doesn't match to expected");

        Assert.assertNotNull(externalFeedEdit.getFeedSelect(), "Feed select doesn't exist");
        Assert.assertNotNull(externalFeedEdit.getTagListInput(), "Tag List field doesn't exist");
        Assert.assertNotNull(externalFeedEdit.getLanguageSelect(), "Language select doesn't exist");
        Assert.assertNotNull(externalFeedEdit.getCategorySelect(), "Category select doesn't exist");
        Assert.assertNotNull(externalFeedEdit.getCompIdInput(), "CompId field doesn't exist");
        Assert.assertNotNull(externalFeedEdit.getActiveCheckbox(), "Active Checkbox doesn't exist");
        Assert.assertNotNull(externalFeedEdit.getSaveButton(), "Save Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
