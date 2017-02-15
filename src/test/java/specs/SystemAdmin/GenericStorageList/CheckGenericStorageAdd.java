package specs.SystemAdmin.GenericStorageList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.testng.annotations.*;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.GenericStorageList.GenericStorageAdd;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-02-15.
 */

public class CheckGenericStorageAdd extends AbstractSpec {
    private static By systemAdminMenuButton, genericStorageListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GenericStorageAdd genericStorageAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData";

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        genericStorageListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_GenericStorageList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        genericStorageAdd = new GenericStorageAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_GenericStorageList");
        sDataFileJson = propUISystemAdmin.getProperty("json_GenericStorageData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

}
