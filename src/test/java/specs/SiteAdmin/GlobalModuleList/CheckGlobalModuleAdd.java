package specs.SiteAdmin.GlobalModuleList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.ModuleDefinitionList.GlobalModuleAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-11.
 */

public class CheckGlobalModuleAdd {
    private static By siteAdminMenuButton, globalModuleListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlobalModuleAdd globalModuleAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", GLOBAL_MODULE_NAME="global_module_name", PAGE_NAME="Global Module";

}
