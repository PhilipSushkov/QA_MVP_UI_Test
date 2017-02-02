package specs.ContentAdmin.Region;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.Region.RegionEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class CheckRegionEdit extends AbstractSpec {

    private static By contentAdminMenuButton, regionListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static RegionEdit regionEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        regionListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_RegionList"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        regionEdit = new RegionEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, regionListMenuItem, addNewLink);
    }

    @Test
    public void checkRegionEdit() throws Exception {
        final String expectedTitle = "Region Edit";

        Assert.assertNotNull(regionEdit.getUrl());
        Assert.assertEquals(regionEdit.getTitle(), expectedTitle, "Actual Region Edit page Title doesn't match to expected");

        Assert.assertNotNull(regionEdit.getRegionNameInput(), "Region Name field doesn't exist");
        Assert.assertNotNull(regionEdit.getGlobalOperationSelect(), "Global Operation select doesn't exist");
        Assert.assertNotNull(regionEdit.getTagsInput(), "Tags field doesn't exist");
        Assert.assertNotNull(regionEdit.getBodyFrame(), "Body frame doesn't exist");

        Assert.assertTrue(regionEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(regionEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
