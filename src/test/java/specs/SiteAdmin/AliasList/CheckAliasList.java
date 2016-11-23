package specs.SiteAdmin.AliasList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.AliasList.AliasList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckAliasList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkAliasList() throws Exception {
        final String expectedTitle = "Alias List";
        final Integer expectedQuantity = 4;

        Assert.assertNotNull(new Dashboard(driver).openAliasListPage().getUrl());
        Assert.assertEquals("Actual Alias List page Title doesn't match to expected", expectedTitle, new AliasList(driver).getTitle());

        //System.out.println(new AliasList(driver).getAliasListQuantity().toString());
        Assert.assertTrue(expectedQuantity == new AliasList(driver).getAliasListQuantity() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
