package specs.ContentAdmin.PressReleaseCategories;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PressReleaseCategories.PressReleaseCategoryEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-04.
 */

public class CheckPressReleaseCategoryEdit extends AbstractSpec {

    private static By contentAdminMenuButton, pressReleaseCategoriesMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PressReleaseCategoryEdit pressReleaseCategoryEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        pressReleaseCategoriesMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_PressReleaseCategories"));
        userAddNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pressReleaseCategoryEdit = new PressReleaseCategoryEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, pressReleaseCategoriesMenuItem, userAddNewLink);
    }

    @Test
    public void checkPressReleaseCategoryEdit() throws Exception {
        final String expectedTitle = "Press Release Category Edit";

        Assert.assertNotNull(pressReleaseCategoryEdit.getUrl());
        Assert.assertEquals(pressReleaseCategoryEdit.getTitle(), expectedTitle, "Actual Press Release Category Edit page Title doesn't match to expected");

        Assert.assertNotNull(pressReleaseCategoryEdit.getCategoryNameInput(), "Category Name field doesn't exist");
        Assert.assertNotNull(pressReleaseCategoryEdit.getLinkToPageSelect(), "Link To Page select doesn't exist");

        Assert.assertTrue(pressReleaseCategoryEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(pressReleaseCategoryEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
