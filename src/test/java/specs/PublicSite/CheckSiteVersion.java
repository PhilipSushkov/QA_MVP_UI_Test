package specs.PublicSite;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageobjects.LiveSite.SiteVersion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import util.Functions;
import util.LocalDriverManager;

/**
 * Created by philipsushkov on 2017-03-28.
 */

public class CheckSiteVersion {
    private static SiteVersion siteVersion;
    private static String sPathToFile, sDataFileJson, sSiteVersion;
    private static JSONParser parser;
    private static WebDriver phDriver;

    private static final int NUM_THREADS = 3;
    private static final String PATHTO_PUBLICSITE_PROP = "PublicSite/PublicSiteMap.properties";
    public static Properties propUIPublicSite;
    private static final String SITE_DATA="siteData";


    @BeforeTest
    public void setUp() throws Exception {
        propUIPublicSite = Functions.ConnectToPropUI(PATHTO_PUBLICSITE_PROP);
        sSiteVersion = propUIPublicSite.getProperty("siteVersion");
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_SiteData");

        parser = new JSONParser();
    }


    @Test(dataProvider=SITE_DATA, threadPoolSize = NUM_THREADS, priority=1)
    public void checkSiteVersion(String site) throws Exception {
        Long id = Thread.currentThread().getId();
        System.out.println("Domain: " + site.toString() + " " +id);

        phDriver = LocalDriverManager.getDriver();
        siteVersion = new SiteVersion(phDriver, site);

        Assert.assertEquals(siteVersion.getSiteVersion(sPathToFile), sSiteVersion);
    }


    @AfterMethod
    public void afterMethod(ITestResult result) {
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                System.out.println(result.getMethod().getMethodName()+": PASS");
                break;

            case ITestResult.FAILURE:
                System.out.println(result.getMethod().getMethodName()+": FAIL");
                break;

            case ITestResult.SKIP:
                System.out.println(result.getMethod().getMethodName()+": SKIP BLOCKED");
                break;

            default:
                throw new RuntimeException(result.getTestName() + "Invalid status");
        }
    }


    @DataProvider(name=SITE_DATA, parallel=true)
    public Object[][] siteData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray siteData = (JSONArray) jsonObject.get("sites");
            ArrayList<String> zoom = new ArrayList();

            for (Iterator<String> iterator = siteData.iterator(); iterator.hasNext();) {
                zoom.add(iterator.next());
            }

            Object[][] newSites = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newSites[i][0] = zoom.get(i);
            }

            return newSites;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    @AfterTest
    public void tearDown() {
        //driver.quit();
    }
}

