package specs.SiteAdmin.AliasList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.AliasList.AliasList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckAliasList extends AbstractSpec {
    private final By siteAdminMenuButton = By.xpath("//span[contains(text(),'Site Admin')]");
    private final By aliasListMenuItem = By.xpath("//a[contains(text(),'Alias List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkAliasList() throws Exception {
        final String expectedTitle = "Alias List";
        final Integer expectedQuantity = 4;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, aliasListMenuItem);

        Assert.assertNotNull(new AliasList(driver).getUrl());
        Assert.assertEquals("Actual Alias List page Title doesn't match to expected", expectedTitle, new AliasList(driver).getTitle());

        //System.out.println(new AliasList(driver).getAliasListQuantity().toString());
        Assert.assertTrue("Actual Alias Quantity is less than expected: "+expectedQuantity, expectedQuantity == new AliasList(driver).getAliasListQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
