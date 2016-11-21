package specs.SiteAdmin.LinkToPageList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.LinkToPageList.LinkToPageList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckLinkToPageList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkLinkToPageList() throws Exception {
        final String expectedTitle = "Link To Page List";
        final Integer expectedQuantity = 15;

        Assert.assertNotNull(new Dashboard(driver).openLinkToPageListPage().getUrl());
        Assert.assertEquals("Actual Link To Page List page Title doesn't match to expected", expectedTitle, new LinkToPageList(driver).getTitle());

        //System.out.println(new LinkToPageList(driver).getLinkToPageListQuantity().toString());
        Assert.assertTrue(expectedQuantity == new LinkToPageList(driver).getLinkToPageListQuantity() );
        Assert.assertNotNull(new LinkToPageList(driver).getLinkToPageListPagination() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
