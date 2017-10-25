package specs.PublicSite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    private static String sPathToFile, sDataSiteJson, sDataModuleJson;
    private static String sSiteVersion, sSiteVersionCookie, sCookie;
    private static String sDataSiteJson_notLive, sDataSiteJson_n, sDataSiteSsl;
    private static JSONParser parser;

    private static final int NUM_THREADS = 7;
    private static final String PATHTO_PUBLICSITE_PROP = "PublicSite/PublicSiteMap.properties";
    public static Properties propUIPublicSite;
    private static final String SITE_DATA="siteData", SITE_DATA_2="siteData2", MODULE_DATA="moduleData", SITE_DATA_SSL="siteDataSsl";
    private static ExtentReports extent, cookieExtent, after;


    @BeforeTest
    public void setUp() throws Exception {
        propUIPublicSite = Functions.ConnectToPropUI(PATHTO_PUBLICSITE_PROP);
        sSiteVersion = propUIPublicSite.getProperty("siteVersion");
        sSiteVersionCookie = propUIPublicSite.getProperty("siteVersionCookie");
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sCookie = propUIPublicSite.getProperty("versionCookie");
        sDataSiteJson = propUIPublicSite.getProperty("json_SiteData");
        sDataSiteJson_notLive = propUIPublicSite.getProperty("json_SiteData_notLive");
        sDataModuleJson = propUIPublicSite.getProperty("json_ModuleData");

        parser = new JSONParser();
        extent = ExtentManager.GetExtent();
        cookieExtent = CookieExtentManager.GetExtent();
        after = AfterExtentManager.GetExtent();

        sDataSiteJson_n = propUIPublicSite.getProperty("json_NgnixSiteData");
        //sDataSiteJson_n = propUIPublicSite.getProperty("json_SiteDataSsl");
        sDataSiteSsl = propUIPublicSite.getProperty("json_SiteDataSsl");
    }

    @Test(dataProvider=SITE_DATA_2, threadPoolSize=NUM_THREADS, priority=1, enabled=false)
    public void checkSiteVersion(String site) throws Exception {
        //crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        String sVersionActual = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getSiteVersion();
        String sUrlActual = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getSiteUrl();
        String sXCacheStatus = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getXCacheStatus(sUrlActual);
        ExtentTest test = extent.createTest("Check version result for " + site);

        if (sVersionActual.equals(sSiteVersion)) {
            test.log(Status.PASS, "Site Version number is valid: " + sSiteVersion);
            test.log(Status.PASS, "Site Url: " + sUrlActual);
            test.log(Status.PASS, "Site X-Cache-Status: " + sXCacheStatus);
        } else {
            test.log(Status.FAIL, "Actual Version number is wrong: " + sVersionActual + ". Supposed to be: " + sSiteVersion);
        }

        Assert.assertEquals(sVersionActual, sSiteVersion, "Site Version number is not correct for " + site);

    }

    @Test(dataProvider=SITE_DATA_2, threadPoolSize=NUM_THREADS, priority=1, enabled=false)
    public void checkSiteVersionAfter(String site) throws Exception {
        //crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        String sSiteVersionAfter = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getSiteVersionAfter();
        String sUrlAfter = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getSiteUrlAfter();
        String sXCacheStatusAfter = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getXCacheStatusAfter(sUrlAfter);
        ExtentTest test = after.createTest("Check version result for " + site);


        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        JSONParser parserAfter = new JSONParser();
        try {
            jsonObjSite = (JSONObject) parserAfter.parse(new FileReader(sPathToFile + site + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        String sSiteVersionBefore = jsonObjSite.get("version_before").toString();
        String sSiteUrlBefore = jsonObjSite.get("Url_before").toString();
        String sSiteXCacheStatusBefore = jsonObjSite.get("X_Cache_Status_Before").toString();

        if (sSiteVersionAfter.equals(sSiteVersionBefore)) {
            test.log(Status.PASS, "After Site Version number is valid: " + sSiteVersionBefore);
        } else {
            test.log(Status.FAIL, "After Site Version number is wrong: " + sSiteVersionAfter + ". Supposed to be: " + sSiteVersionBefore);
        }

        if (sUrlAfter.equals(sSiteUrlBefore)) {
            test.log(Status.PASS, "After Site Url is valid: " + sSiteUrlBefore);
        } else {
            test.log(Status.FAIL, "After Site Url is wrong: " + sUrlAfter + ". Supposed to be: " + sSiteUrlBefore);
        }

        if (sXCacheStatusAfter.equals(sSiteXCacheStatusBefore)) {
            test.log(Status.PASS, "After Site X-Cache-Status is valid: " + sSiteXCacheStatusBefore);
        } else {
            test.log(Status.FAIL, "After Site X-Cache-Status is wrong: " + sXCacheStatusAfter + ". Supposed to be: " + sSiteXCacheStatusBefore);
        }

        Assert.assertEquals(sSiteVersionAfter, sSiteVersionBefore, "After Site Version number is not correct for " + site);
        Assert.assertEquals(sUrlAfter, sSiteUrlBefore, "After Site Url is not correct for " + site);

    }

    @Test(dataProvider=SITE_DATA_2, threadPoolSize=NUM_THREADS, priority=2, enabled=false)
    public void checkSiteVersionCookie(String site) throws Exception {
        //crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        String sVersionActual = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getSiteVersionCookie(sCookie);
        ExtentTest test = cookieExtent.createTest("Check Cookie version result for " + site);

        if (sVersionActual.equals(sSiteVersionCookie)) {
            test.log(Status.PASS, "Site Cookie Version number is valid: " + sSiteVersionCookie);
        } else {
            test.log(Status.FAIL, "Actual Version number is wrong: " + sVersionActual + ". Supposed to be: " + sSiteVersionCookie);
        }

        Assert.assertEquals(sVersionActual, sSiteVersionCookie, "Site Cookie Version number is not correct for " + site);

    }

    @Test(dataProvider=SITE_DATA, threadPoolSize=NUM_THREADS, priority=3, enabled=false)
    public void crawlSiteMap(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertTrue(crawlingSite.getSiteMap(), "sitemap.ashx file doesn't exist for " + site);
    }

    @Test(dataProvider=SITE_DATA, threadPoolSize=NUM_THREADS, priority=4, enabled=false)
    public void crawlSiteModule(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertTrue(crawlingSite.mapSiteModule(sDataModuleJson), "Mapping Site-Modules isn't done properly " + site);
    }

    @Test(dataProvider=SITE_DATA, threadPoolSize=NUM_THREADS, priority=5, enabled=false)
    public void checkSiteModule(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertTrue(crawlingSite.getSiteModule(), "Some Modules on the site didn't find " + site);
    }

    @Test(dataProvider=MODULE_DATA, priority=5, enabled=false)
    public void checkModule(JSONObject module) throws Exception {
        String moduleName = module.get("name").toString();

        Long id = Thread.currentThread().getId();
        System.out.println("Module: " + moduleName + " " +id);
    }

    @Test(dataProvider=SITE_DATA_SSL, priority=6, enabled=false)
    public void checkSslSertificates(String site) throws Exception {
        crawlingSite = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile);
        Assert.assertTrue(crawlingSite.getSslTrust(), "Some Ssl Certificates are failed: " + site);
    }



    @Test(dataProvider=SITE_DATA_2, threadPoolSize=NUM_THREADS, priority=1, enabled=true)
    public void checkStockPriceHeader(String site) throws Exception {
        String sTradeDate = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getTradeDate();
        String sStockPrice = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getStockPrice();
    }

    @Test(dataProvider=SITE_DATA_2, threadPoolSize=NUM_THREADS, priority=1, enabled=false)
    public void checkStockPriceHeader30(String site) throws Exception {
        String sTradeDate30 = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getTradeDate30();
        String sStockPrice30 = new CrawlingSite(LocalDriverManager.getDriver(), site, sPathToFile).getStockPrice30();

        JSONObject jsonObjSite = new JSONObject();
        //System.out.println(sPathToFile + sSite + ".json");

        JSONParser parser30 = new JSONParser();
        try {
            jsonObjSite = (JSONObject) parser30.parse(new FileReader(sPathToFile + site + ".json"));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        String sTradeDate = jsonObjSite.get("trade_date").toString();
        String sStockPrice = jsonObjSite.get("stock_price").toString();

        Assert.assertEquals(sTradeDate30, sTradeDate, "Trade Date value doesn't update for " + site);
        Assert.assertEquals(sStockPrice30, sStockPrice, "Stock Price value doesn't update for " + site);

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

    @DataProvider(name=SITE_DATA_2, parallel=true)
    public Object[][] siteData2() {

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(sPathToFile + sDataSiteJson_n));
            ArrayList<String> zoom = new ArrayList();

            for (Iterator<JSONObject> iterator = jsonArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObject = iterator.next();
                zoom.add(jsonObject.get("Site").toString());
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

    @DataProvider(name=SITE_DATA_SSL, parallel=true)
    public Object[][] siteDataSsl() {

        try {
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(sPathToFile + sDataSiteSsl));
            ArrayList<String> zoom = new ArrayList();

            for (Iterator<JSONObject> iterator = jsonArray.iterator(); iterator.hasNext();) {
                JSONObject jsonObject = iterator.next();
                zoom.add(jsonObject.get("Site").toString());
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

    @AfterTest(alwaysRun=true)
    public void tearDown() {
        //phDriver.quit();
        extent.flush();
        cookieExtent.flush();
        after.flush();
    }
}

