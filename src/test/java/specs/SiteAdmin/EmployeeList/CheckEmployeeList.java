package specs.SiteAdmin.EmployeeList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.EmployeeList.EmployeeList;
import specs.AbstractSpec;

/**
 * Created by andyp on 2017-05-29.
 */
public class CheckEmployeeList extends AbstractSpec{
    private static By siteAdminMenuButton, employeeListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EmployeeList employeeList;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        employeeListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_EmployeeList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        employeeList = new EmployeeList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkExternalFeedList() throws Exception {

        final String expectedTitle = "Employee List";
        final Integer expectedQuantity = 3;

        dashboard.openPageFromMenu(siteAdminMenuButton, employeeListMenuItem);

        Assert.assertNotNull(employeeList.getUrl());
        Assert.assertEquals(expectedTitle, employeeList.getTitle(), "Actual External Feed List page Title doesn't match to expected");

        //System.out.println(externalFeedList.getExternalFeedListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= employeeList.getEmployeeListQuantity(), "Actual Description Quantity is less than expected: "+expectedQuantity);

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}

