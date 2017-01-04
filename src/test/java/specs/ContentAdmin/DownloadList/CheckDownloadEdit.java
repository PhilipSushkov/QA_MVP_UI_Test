package specs.ContentAdmin.DownloadList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.DownloadList.DownloadEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckDownloadEdit extends AbstractSpec {

    private static By contentAdminMenuButton, downloadListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DownloadEdit downloadEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        downloadListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_DownloadList"));
        userAddNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        downloadEdit = new DownloadEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, downloadListMenuItem, userAddNewLink);
    }

    @Test
    public void checkDownloadEdit() throws Exception {
        final String expectedTitle = "Download Edit";

        Assert.assertNotNull(downloadEdit.getUrl());
        Assert.assertEquals(downloadEdit.getTitle(), expectedTitle, "Actual Download Edit page Title doesn't match to expected");

        Assert.assertNotNull(downloadEdit.getDownloadDateInput(), "Download Date field doesn't exist");
        Assert.assertNotNull(downloadEdit.getDownloadTypeSelect(), "Download Type select doesn't exist");
        Assert.assertNotNull(downloadEdit.getDownloadTitleTextarea(), "Download Title textarea doesn't exist");
        Assert.assertNotNull(downloadEdit.getDownloadDescriptionTextarea(), "Download Description textarea doesn't exist");
        Assert.assertNotNull(downloadEdit.getTagsInput(), "Tags field doesn't exist");

        Assert.assertTrue(downloadEdit.getRadioDownloadTypeSet(), "Radio Download Type elements don't exist");

        Assert.assertNotNull(downloadEdit.getDownloadPathInput(), "Download Path field doesn't exist");

        Assert.assertTrue(downloadEdit.getThumbnailSet(), "Thumbnail Path and Image don't exist");
        Assert.assertTrue(downloadEdit.getIconSet(), "Icon Path and Image don't exist");

        Assert.assertNotNull(downloadEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
