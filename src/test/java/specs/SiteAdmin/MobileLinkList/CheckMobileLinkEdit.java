package specs.SiteAdmin.MobileLinkList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.MobileLinkList.MobileLinkEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class CheckMobileLinkEdit extends AbstractSpec {

    private static By siteAdminMenuButton, mobileLinkListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MobileLinkEdit mobileLinkEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        mobileLinkListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_MobileLinkList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mobileLinkEdit = new MobileLinkEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, mobileLinkListMenuItem, userAddNewLink);
    }

    @Test
    public void checkMobileLinkEdit() throws Exception {
        final String expectedTitle = "Mobile Link Edit";

        Assert.assertNotNull(mobileLinkEdit.getUrl());
        Assert.assertEquals(mobileLinkEdit.getTitle(), expectedTitle, "Actual Mobile Link Edit page Title doesn't match to expected");

        Assert.assertNotNull(mobileLinkEdit.getPageSelect(), "Page select doesn't exist");
        Assert.assertNotNull(mobileLinkEdit.getMobileViewSelect(), "Mobile View select doesn't exist");
        Assert.assertNotNull(mobileLinkEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
