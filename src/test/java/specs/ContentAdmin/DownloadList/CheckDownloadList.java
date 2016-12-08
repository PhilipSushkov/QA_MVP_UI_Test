package specs.ContentAdmin.DownloadList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.ContentAdmin.DownloadList.DownloadList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckDownloadList extends AbstractSpec {
    private final By contentAdminMenuButton = By.xpath("//span[contains(text(),'Content Admin')]");
    private final By downloadListMenuItem = By.xpath("//a[contains(text(),'Download List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkDownloadList() throws Exception {
        final String expectedTitle = "Download List";
        final Integer expectedQuantity = 3;

        new Dashboard(driver).openPageFromMenu(contentAdminMenuButton, downloadListMenuItem);

        Assert.assertNotNull(new DownloadList(driver).getUrl());
        Assert.assertEquals("Actual Download List page Title doesn't match to expected", expectedTitle, new DownloadList(driver).getTitle());

        //System.out.println(new DownloadList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Download List Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new DownloadList(driver).getTitleQuantity() );
        Assert.assertNotNull("Download List Pagination doesn't exist", new DownloadList(driver).getDownloadListPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", new DownloadList(driver).getFilterByTag() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
