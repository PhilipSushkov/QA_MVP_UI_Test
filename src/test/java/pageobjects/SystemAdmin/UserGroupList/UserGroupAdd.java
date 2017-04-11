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
    private static By moduleTitle, userGroupNameField, copyPermissionsFrom;
    private static By addNewLink;
    private static By activeChk, saveBtn, cancelBtn, deleteBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="User Group";

    public UserGroupAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        userGroupNameField = By.xpath(propUISystemAdmin.getProperty("input_UserGroupName"));
        copyPermissionsFrom = By.xpath(propUISystemAdmin.getProperty("select_CopyPermissionsFrom"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));
        activeChk = By.xpath(propUISystemAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISystemAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISystemAdmin.getProperty("btn_Delete"));

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
        String user_group_name, permissions_from;
        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            user_group_name = data.get("user_group_name").toString();
            findElement(userGroupNameField).sendKeys(user_group_name);
            jsonObj.put("user_group_name", user_group_name);

            permissions_from = data.get("permissions_from").toString();
            findElement(copyPermissionsFrom).sendKeys(permissions_from);
            jsonObj.put("permissions_from", permissions_from);

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    findElement(activeChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                } else {
                    findElement(activeChk).click();
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

    public Boolean checkUserGroup(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_group_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_group_name = jsonObj.get("user_group_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_group_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(userGroupNameField).getAttribute("value").equals(user_group_name)) {

                // Compare field values with entry data
                try {
                    if (!new Select(findElement(copyPermissionsFrom)).getFirstSelectedOption().getText().equals(data.get("permissions_from").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                    return false;
                }

                try {
                    if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                    return false;
                }

                // Save Url
                URL url = new URL(getUrl());
                String[] params = url.getQuery().split("&");
                JSONObject jsonURLQuery = new JSONObject();
                for (String param:params) {
                    jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
                }
                jsonObj.put("url_query", jsonURLQuery);

                int UserGroupID = Integer.parseInt(jsonURLQuery.get("UserGroupID").toString());

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
                return UserGroupID > 0;
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean editUserGroup(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_group_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_group_name = jsonObj.get("user_group_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_group_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);

            waitForElement(deleteBtn);

            try {
                if (!data.get("permissions_from_ch").toString().isEmpty()) {
                    String permissions_from_ch = data.get("permissions_from_ch").toString();
                    findElement(copyPermissionsFrom).sendKeys(permissions_from_ch);
                    jsonObj.put("permissions_from", permissions_from_ch);
                }
            } catch (NullPointerException e) {}

            try {
                if (!data.get("active_ch").toString().isEmpty()) {
                    if (Boolean.parseBoolean(data.get("active_ch").toString())) {
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
                }
            } catch (NullPointerException e) {
            }

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

    public Boolean checkUserGroupCh(JSONObject data, String name) {
        JSONObject jsonMain;
        JSONObject jsonObj = new JSONObject();
        String user_group_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_group_name = jsonObj.get("user_group_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_group_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(userGroupNameField).getAttribute("value").equals(user_group_name)) {

                // Compare field values with changed data
                try {
                    if (!findElement(activeChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!new Select(findElement(copyPermissionsFrom)).getFirstSelectedOption().getText().equals(data.get("permissions_from_ch").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
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

    public Boolean removeUserGroup (String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String user_group_name;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        user_group_name = jsonObj.get("user_group_name").toString();

        By editBtn = By.xpath("//td[(text()='" + user_group_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            waitForElement(deleteBtn);

            if (findElement(userGroupNameField).getAttribute("value").equals(user_group_name)) {

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
        String  sUserGroupID = JsonPath.read(obj, "$.['"+name+"'].url_query.UserGroupID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?UserGroupID="+sUserGroupID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
