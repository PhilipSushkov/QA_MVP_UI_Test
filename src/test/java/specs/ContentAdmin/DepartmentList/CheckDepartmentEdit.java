package specs.ContentAdmin.DepartmentList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.DepartmentList.DepartmentEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckDepartmentEdit extends AbstractSpec {

    private static By contentAdminMenuButton, departmentListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DepartmentEdit departmentEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        departmentListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_DepartmentList"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        departmentEdit = new DepartmentEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, departmentListMenuItem, addNewLink);
    }

    @Test
    public void checkDepartmentEdit() throws Exception {
        final String expectedTitle = "Department Edit";

        Assert.assertNotNull(departmentEdit.getUrl());
        Assert.assertEquals(departmentEdit.getTitle(), expectedTitle, "Actual Department Edit page Title doesn't match to expected");

        Assert.assertNotNull(departmentEdit.getDepartmentNameInput(), "Department Name input doesn't exist");

        Assert.assertTrue(departmentEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(departmentEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
