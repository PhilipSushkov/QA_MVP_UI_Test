package specs.SystemAdmin.UserList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.UserList.UserList;

/**
 * Created by philipsushkov on 2016-11-10.
 */

public class CheckUserList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkUserList() throws Exception {
        final String expectedTitle = "User List";
        final Integer expectedQuantity = 30;

        Assert.assertNotNull(new Dashboard(driver).openUserListPage().getUrl());
        Assert.assertEquals("Actual User List page Title doesn't match to expected", expectedTitle, new UserList(driver).getTitle());

        //System.out.println(new UserList(driver).getUserNameQuantity().toString());
        Assert.assertTrue("Actual User Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new UserList(driver).getUserNameQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
