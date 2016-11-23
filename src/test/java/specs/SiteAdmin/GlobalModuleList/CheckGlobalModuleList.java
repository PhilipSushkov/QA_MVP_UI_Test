package specs.SiteAdmin.GlobalModuleList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import pageobjects.SiteAdmin.GlobalModuleList.GlobalModuleList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckGlobalModuleList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkGlobalModuleList() throws Exception {
        final String expectedTitle = "Global Module List";
        final Integer expectedQuantity = 10;

        Assert.assertNotNull(new Dashboard(driver).openGlobalModuleListPage().getUrl());
        Assert.assertEquals("Actual Global Module List page Title doesn't match to expected", expectedTitle, new GlobalModuleList(driver).getTitle());

        //System.out.println(new GlobalModuleList(driver).getGlobalModuleListQuantity().toString());
        Assert.assertTrue("Actual Module Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new GlobalModuleList(driver).getGlobalModuleListQuantity() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
