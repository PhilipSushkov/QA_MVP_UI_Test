package specs.ContentAdmin.DepartmentList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.ContentAdmin.DepartmentList.DepartmentList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class CheckDepartmentList extends AbstractSpec {
    private final By contentAdminMenuButton = By.xpath("//span[contains(text(),'Content Admin')]");
    private final By departmentListMenuItem = By.xpath("//a[contains(text(),'Department List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkDepartmentList() throws Exception {
        final String expectedTitle = "Department List";
        final Integer expectedQuantity = 2;

        new Dashboard(driver).openPageFromMenu(contentAdminMenuButton, departmentListMenuItem);

        Assert.assertNotNull(new DepartmentList(driver).getUrl());
        Assert.assertEquals("Actual Department List page Title doesn't match to expected", expectedTitle, new DepartmentList(driver).getTitle());

        //System.out.println(new DepartmentList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Department Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new DepartmentList(driver).getTitleQuantity() );
        //Assert.assertNotNull("Department drop down list doesn't exist", new DepartmentList(driver).getDepartmentList() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
