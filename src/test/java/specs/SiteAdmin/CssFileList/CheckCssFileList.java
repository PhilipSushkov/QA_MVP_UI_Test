package specs.SiteAdmin.CssFileList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import pageobjects.Dashboard.Dashboard;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.CssFileList.CssFileList;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class CheckCssFileList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkCssFileList() throws Exception {

        final String expectedTitle = "Css File List";
        final Integer expectedQuantity = 2;

        Assert.assertNotNull(new Dashboard(driver).openCssFileListPage().getUrl());
        Assert.assertEquals("Actual Css File List page Title doesn't match to expected", expectedTitle, new CssFileList(driver).getTitle());

        //System.out.println(new CssFileList(driver).getCssFileListQuantity().toString());
        Assert.assertTrue(expectedQuantity <= new CssFileList(driver).getCssFileListQuantity() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
