package specs.api.AdminWeb.EuroNews;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.api.AdminWeb.Auth;
import pageobjects.api.AdminWeb.EuroNews.EuroNews;
import pageobjects.api.AdminWeb.LeftMainMenu;
import specs.ApiAbstractSpec;

import com.jayway.jsonpath.JsonPath;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class CheckEuroNewsClientList extends ApiAbstractSpec {
    private static Auth auth;
    private static LeftMainMenu leftMainMenu;
    private static EuroNews euroNews;
    private static final String EURO_NEWS = "Euro News";
    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;
    private final String DATA="getData", REQUESTS="requests";

    @BeforeTest
    public void setUp() throws InterruptedException {
        auth = new Auth(driver);
        leftMainMenu = new LeftMainMenu(driver);

        sPathToFile = System.getProperty("user.dir") + propAPI.getProperty("dataPath_EuroNewsClientList");
        sDataFileJson = propAPI.getProperty("json_EuroNewsClientListData");

        parser = new JSONParser();

        // Authorization
        auth.getGoogleAuthPage();

        // Open Web Section
        auth.getWebSection();

        euroNews = new EuroNews(driver, proxy);

        // Open Euro News Client List page in Web Section
        leftMainMenu.getEuroNewsClientListPage(EURO_NEWS);
    }

    @Test(priority=1)
    public void checkEuroNewsClient() throws InterruptedException {
        final String expectedTitle = "Euro News Client List";

        Assert.assertNotNull(euroNews.getUrl());
        Assert.assertEquals(euroNews.getTitle(), expectedTitle, "Actual Euro News Client List page Title doesn't match to expected");
        Assert.assertNotNull(euroNews.getSearchInput(), "Search field doesn't exist");
        Assert.assertNotNull(euroNews.getClientsDataTable(), "Clients Data table doesn't exist");
        Assert.assertNotNull(euroNews.getPage4Href(), "Page #4 link doesn't exist");
        Assert.assertNotNull(euroNews.getWidgetContent(), "Widget Content doesn't exist");
        Assert.assertNotNull(euroNews.getCellDataSpan(), "Cell Data spans don't exist");
    }

    @Test(priority=2)
    public void checkHarFile() throws InterruptedException {
        Assert.assertNotNull(euroNews.getHar(), "HAR file didn't create");
    }

    @Test(dataProvider=DATA, priority=3)
    public void checkResponseData(JSONObject data) throws InterruptedException {
        Assert.assertNotNull(euroNews.getResponseData(data), "HAR file didn't create");
        String sDomain = JsonPath.read(data, "$.domain");
        //System.out.println(sDomain);
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get(REQUESTS);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject pageObj = (JSONObject) jsonArray.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(jsonArray.get(i));
                }
            }

            Object[][] data = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                data[i][0] = zoom.get(i);
            }

            return data;

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
    }


}
