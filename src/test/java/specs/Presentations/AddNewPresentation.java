package specs.Presentations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Presentations.Presentations;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class AddNewPresentation extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    private Date current = new Date();

    private SimpleDateFormat fullDateF = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
    private SimpleDateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
    private SimpleDateFormat hourF = new SimpleDateFormat("h");
    private SimpleDateFormat minF = new SimpleDateFormat("mm");
    private SimpleDateFormat AMPMF = new SimpleDateFormat("a");

    private String headline = "Exciting testing news! v: " + fullDateF.format(current);
    private String date = dateF.format(current);
    private String hour = hourF.format(current);
    private String min = minF.format(current);
    private String AMPM = AMPMF.format(current);


    @Test
    public void canAddNewPresentation() throws Exception {

        String dashboardURL = new Dashboard(driver).getURL();
        String[] filenames = new String[2];

        String newsPageURL = new Dashboard(driver).newPresentation().addNewPresentation(headline, date, hour, min, AMPM, filenames);

    }


    @After
    public void tearDown() {
        driver.quit();
    }

}
