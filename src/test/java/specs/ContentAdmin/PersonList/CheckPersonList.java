package specs.ContentAdmin.PersonList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.PersonList.PersonList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckPersonList extends AbstractSpec {
    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkPersonList() throws Exception {
        final String expectedTitle = "Person List";
        final Integer expectedQuantity = 1;

        Assert.assertNotNull(new Dashboard(driver).openPersonList().getUrl());
        Assert.assertEquals("Actual Person List page Title doesn't match to expected", expectedTitle, new PersonList(driver).getTitle());

        //System.out.println(new PersonList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Person List Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new PersonList(driver).getTitleQuantity() );
        //Assert.assertNotNull("Person List Pagination doesn't exist", new PersonList(driver).getQuickLinksPagination() );
        Assert.assertNotNull("Department drop down list doesn't exist", new PersonList(driver).getDepartmentList() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
