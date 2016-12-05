package specs.ContentAdmin.FaqList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.FaqList.FaqList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class CheckFaqList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkFaqList() throws Exception {
        final String expectedTitle = "Faq List";
        final Integer expectedQuantity = 1;

        Assert.assertNotNull(new Dashboard(driver).openFaqList().getUrl());
        Assert.assertEquals("Actual Faq List page Title doesn't match to expected", expectedTitle, new FaqList(driver).getTitle());

        //System.out.println(new FaqList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Faq List Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new FaqList(driver).getTitleQuantity() );
        Assert.assertNotNull("Faq List Pagination doesn't exist", new FaqList(driver).getFaqListPagination() );
        //Assert.assertNotNull("Filter By Tag field doesn't exist", new FaqList(driver).getFilterByTag() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
