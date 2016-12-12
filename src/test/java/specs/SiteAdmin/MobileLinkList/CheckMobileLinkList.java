package specs.SiteAdmin.MobileLinkList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.MobileLinkList.MobileLinkList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckMobileLinkList extends AbstractSpec {
    private static By siteAdminMenuButton, mobileLinkListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MobileLinkList mobileLinkList;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        mobileLinkListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_MobileLinkList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mobileLinkList = new MobileLinkList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkMobileLinkList() throws Exception {

        final String expectedTitle = "Mobile Link List";
        final Integer expectedQuantity = 10;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, mobileLinkListMenuItem);

        Assert.assertNotNull(mobileLinkList.getUrl());
        Assert.assertEquals("Actual Mobile Link List page Title doesn't match to expected", expectedTitle, mobileLinkList.getTitle());

        //System.out.println(new MobileLinkList(driver).getMobileLinkListQuantity().toString());
        Assert.assertTrue("Actual Mobile Link Quantity is less than expected: "+expectedQuantity, expectedQuantity <= mobileLinkList.getMobileLinkListQuantity() );
        Assert.assertNotNull("Mobile View pagination doesn't exist", mobileLinkList.getMobileLinkListPagination() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
