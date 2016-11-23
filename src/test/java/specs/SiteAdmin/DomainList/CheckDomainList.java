package specs.SiteAdmin.DomainList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.DomainList.DomainList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckDomainList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkDomainList() throws Exception {
        final String expectedTitle = "Domain List";
        final Integer expectedQuantity = 2;

        Assert.assertNotNull(new Dashboard(driver).openDomainListPage().getUrl());
        Assert.assertEquals("Actual Domain List page Title doesn't match to expected", expectedTitle, new DomainList(driver).getTitle());

        //System.out.println(new DomainList(driver).getDomainQuantity().toString());
        Assert.assertTrue(expectedQuantity <= new DomainList(driver).getDomainQuantity() );
        Assert.assertNotNull(new DomainList(driver).getHrefPublicSite() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
