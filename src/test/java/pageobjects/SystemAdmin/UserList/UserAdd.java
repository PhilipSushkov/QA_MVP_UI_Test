package pageobjects.SystemAdmin.UserList;

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
 * Created by philipsushkov on 2017-04-03.
 */

public class UserAdd extends AbstractPageObject {
    private static By moduleTitle, emailField, userNameField, passwordField;
    private static By addNewLink, saveBtn, cancelBtn, deleteBtn, rolesListChk;
    private static By receivesWorkflowEmailChk, receivesDigestEmailChk, activeChk, sitesListChk;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="User";

    public UserAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        emailField = By.xpath(propUISystemAdmin.getProperty("input_Email"));
        userNameField = By.xpath(propUISystemAdmin.getProperty("input_UserName2"));
        passwordField = By.xpath(propUISystemAdmin.getProperty("chk_ChangePassword"));
        rolesListChk = By.xpath(propUISystemAdmin.getProperty("chk_RolesList"));
        saveBtn = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISystemAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISystemAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));
        receivesWorkflowEmailChk = By.xpath(propUISystemAdmin.getProperty("chk_ReceivesWorkflowEmail"));
        receivesDigestEmailChk = By.xpath(propUISystemAdmin.getProperty("chk_ReceivesDigestEmail"));
        activeChk = By.xpath(propUISystemAdmin.getProperty("chk_Active"));
        sitesListChk = By.xpath(propUISystemAdmin.getProperty("chk_SiteList"));;
        //successMsg = By.xpath(propUISystemAdmin.getProperty("msg_Success"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_WorkflowEmailList");
        sFileJson = propUISystemAdmin.getProperty("json_WorkflowEmails");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

}
