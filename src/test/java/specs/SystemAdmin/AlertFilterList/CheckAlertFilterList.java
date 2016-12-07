package specs.SystemAdmin.AlertFilterList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterList;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class CheckAlertFilterList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkAlertFilterList() throws Exception {
        final String expectedTitle = "Alert Filter List";
        final Integer expectedQuantity = 4;

        Assert.assertNotNull(new Dashboard(driver).openAlertFilterListPage().getUrl());
        Assert.assertEquals("Actual Alert Filter List page Title doesn't match to expected", expectedTitle, new AlertFilterList(driver).getTitle());

        //System.out.println(new AlertFilterList(driver).getFilterNameQuantity().toString());
        Assert.assertTrue("Actual Filter Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new AlertFilterList(driver).getFilterNameQuantity() );
    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }
}
