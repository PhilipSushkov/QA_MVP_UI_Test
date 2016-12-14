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
    private static By contentAdminMenuButton, downloadListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DownloadList downloadList;

    @Before
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        downloadListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_DownloadList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        downloadList = new DownloadList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkDownloadList() throws Exception {
        final String expectedTitle = "Download List";
        final Integer expectedQuantity = 3;

        dashboard.openPageFromMenu(contentAdminMenuButton, downloadListMenuItem);

        Assert.assertNotNull(downloadList.getUrl());
        Assert.assertEquals("Actual Download List page Title doesn't match to expected", expectedTitle, downloadList.getTitle());

        //System.out.println(new DownloadList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Download List Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= downloadList.getTitleQuantity() );
        Assert.assertNotNull("Download List Pagination doesn't exist", downloadList.getDownloadListPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", downloadList.getFilterByTag() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
