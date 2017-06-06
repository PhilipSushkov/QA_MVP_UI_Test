package specs.SiteAdmin.EmployeeList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.EmployeeList.EmployeeEdit;
import specs.AbstractSpec;

/**
 * Created by andyp on 2017-06-06.
 */
public class CheckEmployeeEdit extends AbstractSpec {
    private static By siteAdminMenuButton, EmployeeListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EmployeeEdit employeeListEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        EmployeeListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_EmployeeList"));
        userAddNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        employeeListEdit = new EmployeeEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, EmployeeListMenuItem, userAddNewLink);
    }

    @Test
    public void checkExternalFeedEdit() throws Exception {
        final String expectedTitle = "Employee Edit";

        Assert.assertNotNull(employeeListEdit.getUrl());
        Assert.assertEquals(employeeListEdit.getTitle(), expectedTitle, "Actual Employee List Edit page Title doesn't match to expected");

        Assert.assertNotNull(employeeListEdit.getEmailInput(), "Email field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getPasswordInput(), "Password field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getFirstNameInput(), "First name field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getLastNameInput(), "Last name field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getJobTitleInput(), "Job title field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getPhoneInput(), "Phone number field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getExtensionInput(), "Extension field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getCellPhoneInput(), "Cellphone field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getLocationInput(), "Location field doesn't exist");
        Assert.assertNotNull(employeeListEdit.getactiveChk(), "Active checkbox doesn't exist");
        Assert.assertNotNull(employeeListEdit.getSaveButton(), "Save Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
