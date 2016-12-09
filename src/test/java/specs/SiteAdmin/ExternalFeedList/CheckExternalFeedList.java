package specs.SiteAdmin.ExternalFeedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.ExternalFeedList.ExternalFeedList;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class CheckExternalFeedList extends AbstractSpec {
    private static By siteAdminMenuButton, externalFeedListMenuItem;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        externalFeedListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ExternalFeedList"));

        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkExternalFeedList() throws Exception {

        final String expectedTitle = "External Feed List";
        final Integer expectedQuantity = 4;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, externalFeedListMenuItem);

        Assert.assertNotNull(new ExternalFeedList(driver).getUrl());
        Assert.assertEquals("Actual External Feed List page Title doesn't match to expected", expectedTitle, new ExternalFeedList(driver).getTitle());

        System.out.println(new ExternalFeedList(driver).getExternalFeedListQuantity().toString());
        Assert.assertTrue("Actual Description Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new ExternalFeedList(driver).getExternalFeedListQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
