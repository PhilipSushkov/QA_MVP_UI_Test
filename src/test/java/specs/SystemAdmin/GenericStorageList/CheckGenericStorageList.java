package specs.SystemAdmin.GenericStorageList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.GenericStorageList.GenericStorageList;

/**
 * Created by philipsushkov on 2016-11-11.
 */
public class CheckGenericStorageList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkGenericStorageList() throws Exception {
        final String expectedTitle = "Generic Storage List";
        final Integer expectedQuantity = 1;

        Assert.assertNotNull(new Dashboard(driver).openGenericStorageListPage().getUrl());
        Assert.assertEquals("Actual Generic Storage List page Title doesn't match to expected", expectedTitle, new GenericStorageList(driver).getTitle());

        //System.out.println(new GenericStorageList(driver).getStorageListHeader().toString());
        Assert.assertTrue(expectedQuantity == new GenericStorageList(driver).getStorageListHeader() );
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
