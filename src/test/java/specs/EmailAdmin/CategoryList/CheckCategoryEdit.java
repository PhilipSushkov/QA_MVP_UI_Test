package specs.EmailAdmin.CategoryList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.EmailAdmin.CategoryList.CategoryEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-10.
 */

public class CheckCategoryEdit extends AbstractSpec {

    private static By emailAdminMenuButton, categoryListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CategoryEdit categoryEdit;

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        categoryListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_CategoryList"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        categoryEdit = new CategoryEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(emailAdminMenuButton, categoryListMenuItem, addNewLink);
    }

    @Test
    public void checkCategoryEdit() throws Exception {
        final String expectedTitle = "Category Edit";

        Assert.assertNotNull(categoryEdit.getUrl());
        Assert.assertEquals(categoryEdit.getTitle(), expectedTitle, "Actual Category Edit page Title doesn't match to expected");

        Assert.assertNotNull(categoryEdit.getCategoryNameInput(), "Category Name field doesn't exist");
        Assert.assertNotNull(categoryEdit.getChkBoxSet(), "Checkbox elements doesn't exist");

        Assert.assertNotNull(categoryEdit.getSaveButton(), "Save button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
