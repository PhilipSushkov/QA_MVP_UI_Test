package specs.PublicSite;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageobjects.LiveSite.CrawlingSite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

import util.Functions;
import util.LocalDriverManager;

/**
 * Created by philipsushkov on 2017-03-28.
 */

public class CheckCrawlingSite {
    private static CrawlingSite crawlingSite;
    private static String sPathToFile, sDataSiteJson, sDataModuleJson, sSiteVersion;
    private static JSONParser parser;

    private static final int NUM_THREADS = 3;
    private static final String PATHTO_PUBLICSITE_PROP = "PublicSite/PublicSiteMap.properties";
    public static Properties propUIPublicSite;
    private static final String SITE_DATA="siteData", MODULE_DATA="moduleData";


    @BeforeTest
    public void setUp() throws Exception {
        propUIPublicSite = Functions.ConnectToPropUI(PATHTO_PUBLICSITE_PROP);
        sSiteVersion = propUIPublicSite.getProperty("siteVersion");
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataSiteJson = propUIPublicSite.getProperty("json_SiteData");
        sDataModuleJson = propUIPublicSite.getProperty("json_ModuleData");

        parser = new JSONParser();
    }

    @Test(dataProvider=SITE_DATA, threadPoolSize=NUM_THREADS, priority=1, enabled=false)
    public void checkSiteVersion(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertEquals(crawlingSite.getSiteVersion(), sSiteVersion, "Site Version number is not correct for " + site);
    }

    @Test(dataProvider=SITE_DATA, threadPoolSize=NUM_THREADS, priority=2, enabled=false)
    public void checkSiteMap(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertTrue(crawlingSite.getSiteMap(), "sitemap.ashx file doesn't exist for " + site);
    }

    @Test(dataProvider=SITE_DATA, threadPoolSize=NUM_THREADS, priority=3, enabled=true)
    public void checkSiteModule(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertTrue(crawlingSite.getSiteModule(sDataModuleJson), "Mapping Site-Modules isn't done properly " + site);
    }

    @Test(dataProvider=MODULE_DATA, priority=4, enabled=false)
    public void checkModule(JSONObject module) throws Exception {
        String moduleName = module.get("name").toString();

        Long id = Thread.currentThread().getId();
        System.out.println("Module: " + moduleName + " " +id);
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        Long id = Thread.currentThread().getId();

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                System.out.println(result.getMethod().getMethodName()+" "+Arrays.toString(result.getParameters())+" ("+id+"): PASS");
                break;

            case ITestResult.FAILURE:
                System.out.println(result.getMethod().getMethodName()+" "+Arrays.toString(result.getParameters())+" ("+id+"): FAIL");
                break;

            case ITestResult.SKIP:
                System.out.println(result.getMethod().getMethodName()+" "+Arrays.toString(result.getParameters())+" ("+id+"): SKIP BLOCKED");
                break;

            default:
                throw new RuntimeException(result.getTestName() + "Invalid status");
        }
    }


    @DataProvider(name=SITE_DATA, parallel=true)
    public Object[][] siteData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataSiteJson));
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

    @DataProvider(name=MODULE_DATA)
    public Object[][] moduleData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataModuleJson));
            JSONArray moduleData = (JSONArray) jsonObject.get("modules");
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < moduleData.size(); i++) {
                JSONObject pageObj = (JSONObject) moduleData.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(moduleData.get(i));
                }
            }

            Object[][] modules = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                modules[i][0] = zoom.get(i);
            }

            return modules;

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

