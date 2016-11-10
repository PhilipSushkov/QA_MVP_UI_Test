package specs.SystemAdmin.UserList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

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
        String dashboardURL = new Dashboard(driver).getURL();

        String userListPageURL = new Dashboard(driver).openUserListPage().getUrl();
        System.out.println(userListPageURL);
        Assert.assertNotNull(userListPageURL);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
