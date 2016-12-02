package specs.ContentAdmin.DepartmentList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.DepartmentList.DepartmentList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckDepartmentList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkDepartmentList() throws Exception {
        final String expectedTitle = "Department List";
        final Integer expectedQuantity = 2;

        Assert.assertNotNull(new Dashboard(driver).openDepartmentList().getUrl());
        Assert.assertEquals("Actual Department List page Title doesn't match to expected", expectedTitle, new DepartmentList(driver).getTitle());

        //System.out.println(new DepartmentList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Department Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new DepartmentList(driver).getTitleQuantity() );
        //Assert.assertNotNull("Department drop down list doesn't exist", new DepartmentList(driver).getDepartmentList() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
