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

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_UserList");
        sFileJson = propUISystemAdmin.getProperty("json_Users");
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


            user_name = data.get("user_name").toString()+randNum;
            findElement(userNameField).sendKeys(user_name);
            jsonObj.put("user_name", user_name);

            email = user_name + data.get("email").toString();
            findElement(emailField).sendKeys(email);
            jsonObj.put("email", email);

            password = data.get("password").toString();
            findElement(passwordField).sendKeys(password);
            jsonObj.put("password", password);

            // Set roles values
            List<WebElement> rolesListChkElements = findElements(rolesListChk);
            Boolean bRolesGroup = false;
            for (WebElement rolesListChkElement:rolesListChkElements)
            {
                //System.out.println(rolesListChkElement.getAttribute("textContent"));
                String group = rolesListChkElement.getAttribute("textContent");

                roles = (JSONArray) data.get("roles");
                jsonObj.put("roles", roles);

                for (Iterator<String> iterator = roles.iterator(); iterator.hasNext(); roles.size()) {
                    String roles_group = iterator.next();
                    if (group.equals(roles_group)) {
                        bRolesGroup = true;
                        break;
                    } else {
                        bRolesGroup = false;
                    }
                }

                By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkRolesList')]");

                if (bRolesGroup) {
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
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    findElement(activeChk).click();
                    //jsonObj.put("active", true);
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                } else {
                    findElement(activeChk).click();
                    //jsonObj.put("active", false);
                }
            }

            // Save Receives Workflow Email checkbox
            receives_workflow_email = Boolean.parseBoolean(data.get("receives_workflow_email").toString());
            jsonObj.put("receives_workflow_email", Boolean.parseBoolean(data.get("receives_workflow_email").toString()));
            if (receives_workflow_email) {
                if (!Boolean.parseBoolean(findElement(receivesWorkflowEmailChk).getAttribute("checked"))) {
                    findElement(receivesWorkflowEmailChk).click();
                    //jsonObj.put("receives_workflow_email", true);
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(receivesWorkflowEmailChk).getAttribute("checked"))) {
                } else {
                    findElement(receivesWorkflowEmailChk).click();
                    //jsonObj.put("receives_workflow_email", false);
                }
            }

            // Save Receives Digest Email checkbox
            receives_digest_email = Boolean.parseBoolean(data.get("receives_digest_email").toString());
            jsonObj.put("receives_digest_email", Boolean.parseBoolean(data.get("receives_digest_email").toString()));
            if (receives_digest_email) {
                if (!Boolean.parseBoolean(findElement(receivesDigestEmailChk).getAttribute("checked"))) {
                    findElement(receivesDigestEmailChk).click();
                    //jsonObj.put("receives_digest_email", true);
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(receivesDigestEmailChk).getAttribute("checked"))) {
                } else {
                    findElement(receivesDigestEmailChk).click();
                    //jsonObj.put("receives_digest_email", false);
                }
            }


            // Set roles values
            List<WebElement> sitesListChkElements = findElements(sitesListChk);
            Boolean bSitesGroup = false;
            for (WebElement sitesListChkElement:sitesListChkElements)
            {
                //System.out.println(rolesListChkElement.getAttribute("textContent"));
                String group = sitesListChkElement.getAttribute("textContent");

                sites = (JSONArray) data.get("sites");
                jsonObj.put("sites", sites);

                for (Iterator<String> iterator = sites.iterator(); iterator.hasNext(); sites.size()) {
                    String site_group = iterator.next();
                    if (group.equals(site_group)) {
                        bSitesGroup = true;
                        break;
                    } else {
                        bSitesGroup = false;
                    }
                }

                By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkSiteList')]");

                if (bSitesGroup) {
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


            findElement(saveBtn).click();
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Boolean checkUser(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_name = jsonObj.get("user_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(userNameField).getAttribute("value").equals(user_name)) {
                //System.out.println("Filter Name is correct");

                // Compare field values with entry data
                try {
                    if (!findElement(emailField).getAttribute("value").contains(data.get("email").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(receivesWorkflowEmailChk).getAttribute("checked").equals(data.get("receives_workflow_email").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(receivesDigestEmailChk).getAttribute("checked").equals(data.get("receives_digest_email").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                // Compare Roles checkbox values with entry data
                List<WebElement> rolesListChkElements = findElements(rolesListChk);
                Boolean bRolesGroup = false;
                for (WebElement rolesListChkElement:rolesListChkElements)
                {
                    //System.out.println(rolesListChkElement.getAttribute("textContent"));
                    String group = rolesListChkElement.getAttribute("textContent");

                    JSONArray roles = (JSONArray) data.get("roles");

                    for (Iterator<String> iterator = roles.iterator(); iterator.hasNext(); roles.size()) {
                        String roles_group = iterator.next();
                        if (group.equals(roles_group)) {
                            bRolesGroup = true;
                            break;
                        } else {
                            bRolesGroup = false;
                        }
                    }

                    By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkRolesList')]");

                    if (bRolesGroup) {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                            return false;
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                        } else {
                            return false;
                        }
                    }

                }


                // Compare Sites checkbox values with entry data
                List<WebElement> sitesListChkElements = findElements(sitesListChk);
                Boolean bSitesGroup = false;
                for (WebElement sitesListChkElement:sitesListChkElements)
                {
                    //System.out.println(rolesListChkElement.getAttribute("textContent"));
                    String group = sitesListChkElement.getAttribute("textContent");

                    JSONArray sites = (JSONArray) data.get("sites");

                    for (Iterator<String> iterator = sites.iterator(); iterator.hasNext(); sites.size()) {
                        String sites_group = iterator.next();
                        if (group.equals(sites_group)) {
                            bSitesGroup = true;
                            break;
                        } else {
                            bSitesGroup = false;
                        }
                    }

                    By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkSiteList')]");

                    if (bSitesGroup) {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                            return false;
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                        } else {
                            return false;
                        }
                    }

                }



                // Save Url
                URL url = new URL(getUrl());
                String[] params = url.getQuery().split("&");
                JSONObject jsonURLQuery = new JSONObject();
                for (String param:params) {
                    jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
                }
                jsonObj.put("url_query", jsonURLQuery);

                int userID = Integer.parseInt(jsonURLQuery.get("UserID").toString());

                try {
                    FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                    writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                    writeFile.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println(name + ": "+PAGE_NAME+" has been checked");
                return userID > 0;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean editUser(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_name = jsonObj.get("user_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);

            waitForElement(deleteBtn);

            try {
                if (!data.get("email_ch").toString().isEmpty()) {
                    String sEmail_ch = user_name + data.get("email_ch").toString();
                    findElement(emailField).clear();
                    findElement(emailField).sendKeys(sEmail_ch);
                    jsonObj.put("email", sEmail_ch);
                }
            } catch (NullPointerException e) {}

            Thread.sleep(DEFAULT_PAUSE);
            findElement(saveBtn).click();

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
            waitForElement(editBtn);

            System.out.println(name + ": " + PAGE_NAME + " has been changed");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean checkUserCh(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_name = jsonObj.get("user_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(userNameField).getAttribute("value").equals(user_name)) {
                //System.out.println("Filter Name is correct");

                // Compare field values with changed data
                try {
                    if (!findElement(emailField).getAttribute("value").contains(user_name + data.get("email_ch").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(receivesWorkflowEmailChk).getAttribute("checked").equals(data.get("receives_workflow_email").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(receivesDigestEmailChk).getAttribute("checked").equals(data.get("receives_digest_email").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                // Compare Roles checkbox values with entry data
                List<WebElement> rolesListChkElements = findElements(rolesListChk);
                Boolean bRolesGroup = false;
                for (WebElement rolesListChkElement:rolesListChkElements)
                {
                    //System.out.println(rolesListChkElement.getAttribute("textContent"));
                    String group = rolesListChkElement.getAttribute("textContent");

                    JSONArray roles = (JSONArray) data.get("roles");

                    for (Iterator<String> iterator = roles.iterator(); iterator.hasNext(); roles.size()) {
                        String roles_group = iterator.next();
                        if (group.equals(roles_group)) {
                            bRolesGroup = true;
                            break;
                        } else {
                            bRolesGroup = false;
                        }
                    }

                    By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkRolesList')]");

                    if (bRolesGroup) {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                            return false;
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                        } else {
                            return false;
                        }
                    }

                }


                // Compare Sites checkbox values with entry data
                List<WebElement> sitesListChkElements = findElements(sitesListChk);
                Boolean bSitesGroup = false;
                for (WebElement sitesListChkElement:sitesListChkElements)
                {
                    //System.out.println(rolesListChkElement.getAttribute("textContent"));
                    String group = sitesListChkElement.getAttribute("textContent");

                    JSONArray sites = (JSONArray) data.get("sites");

                    for (Iterator<String> iterator = sites.iterator(); iterator.hasNext(); sites.size()) {
                        String sites_group = iterator.next();
                        if (group.equals(sites_group)) {
                            bSitesGroup = true;
                            break;
                        } else {
                            bSitesGroup = false;
                        }
                    }

                    By chkBox = By.xpath("//label[(text()='" + group + "')]/parent::td/input[contains(@id, 'chkSiteList')]");

                    if (bSitesGroup) {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                            return false;
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(chkBox).getAttribute("checked"))) {
                        } else {
                            return false;
                        }
                    }

                }

                System.out.println(name + ": "+PAGE_NAME+" changes have been checked");
                return true;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean removeUser (String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_name = jsonObj.get("user_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            waitForElement(deleteBtn);

            if (findElement(userNameField).getAttribute("value").equals(user_name)) {

                findElement(deleteBtn).click();

                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(moduleTitle);

                try {
                    waitForElement(editBtn);
                } catch (TimeoutException e) {

                    jsonMain.remove(name);

                    try {
                        FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                        writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                        writeFile.flush();
                    } catch (FileNotFoundException e1) {
                        e.printStackTrace();
                    } catch (IOException e1) {
                        e.printStackTrace();
                    }

                    System.out.println(name + ": " + PAGE_NAME + " has been removed");
                    return true;
                }

                return false;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getPageUrl (JSONObject obj, String name) {
        String  sUserID = JsonPath.read(obj, "$.['"+name+"'].url_query.UserID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?UserID="+sUserID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
