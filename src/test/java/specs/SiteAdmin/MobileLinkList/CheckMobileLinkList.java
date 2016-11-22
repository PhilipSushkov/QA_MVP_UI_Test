package specs.SiteAdmin.MobileLinkList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.MobileLinkList.MobileLinkList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckMobileLinkList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkMobileLinkList() throws Exception {

        final String expectedTitle = "Mobile Link List";
        final Integer expectedQuantity = 10;

        Assert.assertNotNull(new Dashboard(driver).openMobileLinkListPage().getUrl());
        Assert.assertEquals("Actual Mobile Link List page Title doesn't match to expected", expectedTitle, new MobileLinkList(driver).getTitle());

        //System.out.println(new MobileLinkList(driver).getMobileLinkListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= new MobileLinkList(driver).getMobileLinkListQuantity() );
        Assert.assertNotNull(new MobileLinkList(driver).getMobileLinkListPagination() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }
}
