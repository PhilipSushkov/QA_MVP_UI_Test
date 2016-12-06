package specs.Dashboard;


import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

public class CheckDashboard extends AbstractSpec{

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canInvalidateCache() throws Exception {
        Assert.assertThat("Invalidate cache message is not displayed",
                new Dashboard(driver).invalidateCache().getInvalidateCacheMessage(),
                CoreMatchers.containsString("Cache is invalidated successfully"));
    }

    @After
    public void tearDown() {
        //driver.quit();
    }
}
