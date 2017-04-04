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
        passwordField = By.xpath(propUISystemAdmin.getProperty("input_Password"));
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

    public String saveUser(JSONObject data, String name) {
        String email, user_name, password;
        Boolean receives_workflow_email, receives_digest_email, active;
        JSONArray roles, sites;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        int randNum = Functions.randInt(0, 99999);

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }


            email = data.get("email").toString();
            findElement(emailField).sendKeys(email);
            jsonObj.put("email", email);

            user_name = data.get("user_name").toString();
            findElement(userNameField).sendKeys(user_name+randNum);
            jsonObj.put("user_name", user_name);

            password = data.get("password").toString();
            findElement(passwordField).sendKeys(password);
            jsonObj.put("password", password);

            // Set roles values
            List<WebElement> rolesListChkElements = findElements(rolesListChk);
            Boolean bGroup = false;
            for (WebElement rolesListChkElement:rolesListChkElements)
            {
                //System.out.println(rolesListChkElement.getAttribute("textContent"));
                String group = rolesListChkElement.getAttribute("textContent");

                roles = (JSONArray) data.get("roles");
                jsonObj.put("roles", roles);

                for (Iterator<String> iterator = roles.iterator(); iterator.hasNext(); roles.size()) {
                    String user_group = iterator.next();
                    if (group.equals(user_group)) {
                        bGroup = true;
                        break;
                    } else {
                        bGroup = false;
                    }
                }

                By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkRolesList')]");

                if (bGroup) {
                    if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                        findElement(chkBox).click();
                        Thread.sleep(500);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                    } else {
                        findElement(chkBox).click();
                        Thread.sleep(500);
                    }
                }

            }

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    findElement(activeChk).click();
                    jsonObj.put("active", true);
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                } else {
                    findElement(activeChk).click();
                    jsonObj.put("active", false);
                }
            }

            /*
            //findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            jsonMain.put(name, jsonObj);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(moduleTitle);

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return findElement(moduleTitle).getText();
            */

        } catch (Exception e) {
            e.printStackTrace();
        }

        //return null;
        return "User List";
    }

    public String getPageUrl (JSONObject obj, String name) {
        String  sFilterId = JsonPath.read(obj, "$.['"+name+"'].url_query.EmailAlertID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?EmailAlertID="+sFilterId+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
