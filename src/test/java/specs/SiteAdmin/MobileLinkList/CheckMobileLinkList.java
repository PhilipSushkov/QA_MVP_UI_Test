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

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        mobileLinkListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_MobileLinkList"));

        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkMobileLinkList() throws Exception {

        final String expectedTitle = "Mobile Link List";
        final Integer expectedQuantity = 10;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, mobileLinkListMenuItem);

        Assert.assertNotNull(new MobileLinkList(driver).getUrl());
        Assert.assertEquals("Actual Mobile Link List page Title doesn't match to expected", expectedTitle, new MobileLinkList(driver).getTitle());

        //System.out.println(new MobileLinkList(driver).getMobileLinkListQuantity().toString());
        Assert.assertTrue("Actual Mobile Link Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new MobileLinkList(driver).getMobileLinkListQuantity() );
        Assert.assertNotNull("Mobile View pagination doesn't exist", new MobileLinkList(driver).getMobileLinkListPagination() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }
}
