package specs.HTMLUnitDriver;

//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

/**
 * Created by philipsushkov on 2016-11-29.
 */

public class MultiThreadTest extends AbstractSpec {

    @BeforeMethod
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkCssFileList() throws Exception {
        System.out.println(browser.getBrowserType());
    }

    @AfterMethod
    public void tearDown() {
        //driver.quit();
    }
}
