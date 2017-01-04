package specs.ContentAdmin.DownloadList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

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

    @BeforeTest
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
        Assert.assertEquals(downloadList.getTitle(), expectedTitle, "Actual Download List page Title doesn't match to expected");

        //System.out.println(new DownloadList(driver).getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= downloadList.getTitleQuantity(), "Actual Download List Title Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(downloadList.getDownloadListPagination(), "Download List Pagination doesn't exist");
        Assert.assertNotNull(downloadList.getFilterByTag(), "Filter By Tag field doesn't exist");
        Assert.assertNotNull(downloadList.getGeneralSelect(), "General select doesn't exist");
        Assert.assertNotNull(downloadList.getPublishButton(), "Publish button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
