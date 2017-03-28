package specs.PublicSite;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by philipsushkov on 2017-03-28.
 */

public class CheckSiteVersion extends AbstractSpec {
    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String SITE_DATA="siteData";

    @BeforeTest
    public void setUp() throws Exception {
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_SiteData");

        parser = new JSONParser();

        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_38);
    }

    @Test(dataProvider=SITE_DATA, priority=1)
    public void checkSiteVersion(String site) throws Exception {
        System.out.println("Domain: " + site.toString());
        Assert.assertTrue(true, "");
    }

    @DataProvider
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

