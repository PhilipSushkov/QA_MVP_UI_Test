package specs.SystemAdmin.UserGroupList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import pageobjects.SystemAdmin.UserGroupList.UserGroupList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckUserGroupList extends AbstractSpec {
    private static By systemAdminMenuButton, userGroupListMenuItem;

    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userGroupListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserGroupList"));

        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkUserGroupList() throws Exception {
        final String expectedTitle = "User Group List";
        final Integer expectedQuantity = 5;

        new Dashboard(driver).openPageFromMenu(systemAdminMenuButton, userGroupListMenuItem);

        Assert.assertNotNull(new UserGroupList(driver).getUrl());
        Assert.assertEquals("Actual User Group List page Title doesn't match to expected", expectedTitle, new UserGroupList(driver).getTitle());

        //System.out.println(new UserGroupList(driver).getUserGroupListQuantity().toString());
        Assert.assertTrue("Actual User Group Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new UserGroupList(driver).getUserGroupListQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
