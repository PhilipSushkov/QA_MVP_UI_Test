package specs.LoginPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import pageobjects.Dashboard.Dashboard;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;



/**
 * Created by philipsushkov on 2016-11-04.
 */
public class EnterToAdmin extends AbstractSpec {

    @Before
    public void setUp() throws Exception {

        //Login to Admin site with valid credentials
        new LoginPage(driver).loginUser();
    }


    @Test
    public void checkSessionID() throws Exception {

        //Get SessionID from Browser Cookies and make assertion
        new Dashboard(driver).getUrl();
        String[] sessionIDCookie = new LoginPage(driver).sessionID();
        //System.out.println(sessionIDCookie[0]+": " +sessionIDCookie[1]);
        Assert.assertNotNull(sessionIDCookie[1]);
    }


    @After
    public void tearDown() {
        //Close Browser
        driver.quit();
    }
}
