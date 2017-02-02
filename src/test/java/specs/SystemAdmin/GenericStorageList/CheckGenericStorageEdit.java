package specs.SystemAdmin.GenericStorageList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.GenericStorageList.GenericStorageEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class CheckGenericStorageEdit extends AbstractSpec {
    private static By systemAdminMenuButton, genericStorageListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GenericStorageEdit genericStorageEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        genericStorageListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_GenericStorageList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        genericStorageEdit = new GenericStorageEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, genericStorageListMenuItem, userAddNewLink);
    }

    @Test
    public void checkGenericStorageEdit() throws Exception {
        final String expectedTitle = "Generic Storage Edit";

        Assert.assertNotNull(genericStorageEdit.getUrl());
        Assert.assertEquals(genericStorageEdit.getTitle(), expectedTitle, "Actual Generic Storage Edit page Title doesn't match to expected");

        Assert.assertNotNull(genericStorageEdit.getDataTokenSelect(), "Data Token Select doesn't exist");
        Assert.assertNotNull(genericStorageEdit.getDataContentTextarea(), "Data Content Textarea doesn't exist");
        Assert.assertNotNull(genericStorageEdit.getSaveAndSubmitButton(), "Save And Submit Button doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
