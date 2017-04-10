package pageobjects.SystemAdmin.UserGroupList;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-04-10.
 */

public class UserGroupAdd extends AbstractPageObject {
    private static By moduleTitle;
    private static By addNewLink;
    private static By activeChk, saveBtn, cancelBtn, deleteBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="User Group";

    public UserGroupAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));
        activeChk = By.xpath(propUISystemAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISystemAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISystemAdmin.getProperty("btn_Delete"));
        //successMsg = By.xpath(propUISystemAdmin.getProperty("msg_Success"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_UserGroupList");
        sFileJson = propUISystemAdmin.getProperty("json_UserGroups");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveUserGroup(JSONObject data, String name) {

        return "User Group List";
    }

    public String getPageUrl (JSONObject obj, String name) {
        String  sFilterId = JsonPath.read(obj, "$.['"+name+"'].url_query.UserID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?UserID="+sFilterId+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
