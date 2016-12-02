package specs.ContentAdmin.DownloadList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.DownloadList.DownloadList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckDownloadList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkDownloadList() throws Exception {
        final String expectedTitle = "Download List";
        final Integer expectedQuantity = 3;

        Assert.assertNotNull(new Dashboard(driver).openDownloadList().getUrl());
        Assert.assertEquals("Actual Download List page Title doesn't match to expected", expectedTitle, new DownloadList(driver).getTitle());

        //System.out.println(new DownloadList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Download List Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new DownloadList(driver).getTitleQuantity() );
        Assert.assertNotNull("Download List Pagination doesn't exist", new DownloadList(driver).getQuickLinksPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", new DownloadList(driver).getFilterByTag() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
