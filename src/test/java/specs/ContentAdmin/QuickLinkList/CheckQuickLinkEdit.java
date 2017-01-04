package specs.ContentAdmin.QuickLinkList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.QuickLinkList.QuickLinkEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckQuickLinkEdit extends AbstractSpec {
    private static By contentAdminMenuButton, quickLinksMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static QuickLinkEdit quickLinkEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        quickLinksMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_QuickLinks"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        quickLinkEdit = new QuickLinkEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, quickLinksMenuItem, addNewLink);
    }

    @Test
    public void checkQuickLinkEdit() throws Exception {
        final String expectedTitle = "Quick Link Edit";

        Assert.assertNotNull(quickLinkEdit.getUrl());
        Assert.assertEquals(quickLinkEdit.getTitle(), expectedTitle, "Actual Quick Link Edit page Title doesn't match to expected");

        Assert.assertNotNull(quickLinkEdit.getDescriptionInput(), "Description field doesn't exist");

        Assert.assertTrue(quickLinkEdit.getRadioTypeSet(), "Radio Type elements don't exist");

        Assert.assertNotNull(quickLinkEdit.getUrlInput(), "Url field doesn't exist");
        Assert.assertNotNull(quickLinkEdit.getTextInput(), "Text field doesn't exist");
        Assert.assertNotNull(quickLinkEdit.getTagsInput(), "Tags field doesn't exist");

        Assert.assertTrue(quickLinkEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertTrue(quickLinkEdit.getQuickLinkPagesSet(), "Quick Link Pages elements don't exist");

        Assert.assertNotNull(quickLinkEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
