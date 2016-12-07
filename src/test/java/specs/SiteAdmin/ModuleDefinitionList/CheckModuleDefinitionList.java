package specs.SiteAdmin.ModuleDefinitionList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import pageobjects.SiteAdmin.ModuleDefinitionList.ModuleDefinitionList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckModuleDefinitionList extends AbstractSpec {
    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkModuleDefinitionList() throws Exception {

        final String expectedTitle = "Module Definition List";
        final Integer expectedQuantity = 10;

        Assert.assertNotNull(new Dashboard(driver).openModuleDefinitionListPage().getUrl());
        Assert.assertEquals("Actual Module Definition List page Title doesn't match to expected", expectedTitle, new ModuleDefinitionList(driver).getTitle());

        //System.out.println(new ModuleDefinitionList(driver).getModuleDefinitionQuantity().toString());
        Assert.assertTrue("Actual Module Definition Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new ModuleDefinitionList(driver).getModuleDefinitionQuantity() );
        Assert.assertNotNull("Module Definition pagination doesn't exist", new ModuleDefinitionList(driver).getModuleDefinitionPagination() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
