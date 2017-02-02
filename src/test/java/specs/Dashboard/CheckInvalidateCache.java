package specs.Dashboard;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

import java.util.Date;

/**
 * Created by philipsushkov on 2016-12-16.
 */
public class CheckInvalidateCache extends AbstractSpec {
    private static LoginPage loginPage;
    private static Dashboard dashboard;

    @BeforeTest
    public void setUp() throws Exception {
        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);

        loginPage.loginUser();
    }

    @Test
    public void canInvalidateCache() throws Exception {
        final String expectedMessage = "Cache is invalidated successfully";

        //System.out.println(dashboard.invalidateCache().getInvalidateCacheMessage());

        // clicking Invalidate Cache button and checking that cache invalidated message appears
        dashboard.invalidateCache();
        Assert.assertEquals(dashboard.getInvalidateCacheMessage(), expectedMessage, "Invalidate cache message is not displayed");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
