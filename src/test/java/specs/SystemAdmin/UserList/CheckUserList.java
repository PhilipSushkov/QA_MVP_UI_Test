package specs.SystemAdmin.UserList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.UserList.UserList;

/**
 * Created by philipsushkov on 2016-11-10.
 */

public class CheckUserList extends AbstractSpec {
    private static By systemAdminMenuButton, userListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserList userList;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userList = new UserList(driver);

        loginPage.loginUser();
        dashboard.openPageFromMenu(systemAdminMenuButton, userListMenuItem);
    }

    @Test
    public void checkUserList() throws Exception {
        final String expectedTitle = "User List";
        final Integer expectedQuantity = 30;

        Assert.assertNotNull(userList.getUrl());
        Assert.assertEquals(userList.getTitle(), expectedTitle, "Actual User List page Title doesn't match to expected");

        //System.out.println(new UserList(driver).getUserNameQuantity().toString());
        Assert.assertTrue(expectedQuantity <= userList.getUserNameQuantity(), "Actual User Name Quantity is less than expected: "+expectedQuantity);

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
