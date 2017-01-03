package specs.SiteAdmin.LinkToPageList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.LinkToPageList.LinkToPageEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class CheckLinkToPageEdit extends AbstractSpec {

    private static By siteAdminMenuButton, linkToPageListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LinkToPageEdit linkToPageEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        linkToPageListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LinkToPageList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        linkToPageEdit = new LinkToPageEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, linkToPageListMenuItem, userAddNewLink);
    }

    @Test
    public void checkLinkToPageEdit() throws Exception {
        final String expectedTitle = "Link To Page Edit";

        Assert.assertNotNull(linkToPageEdit.getUrl());
        Assert.assertEquals(linkToPageEdit.getTitle(), expectedTitle, "Actual Link To Page Edit page Title doesn't match to expected");

        Assert.assertNotNull(linkToPageEdit.getKeyNameInput(), "Key Name field doesn't exist");
        Assert.assertNotNull(linkToPageEdit.getLinkToPageSelect(), "Link To Page select doesn't exist");
        Assert.assertNotNull(linkToPageEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
