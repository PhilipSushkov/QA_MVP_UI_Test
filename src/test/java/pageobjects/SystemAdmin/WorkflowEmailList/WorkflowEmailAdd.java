package pageobjects.SystemAdmin.WorkflowEmailList;

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
 * Created by philipsushkov on 2017-03-06.
 */

public class WorkflowEmailAdd extends AbstractPageObject {
    private static By moduleTitle, descriptionField, systemTaskSelect, systemMessageSelect;
    private static By addNewLink, saveBtn, cancelBtn, deleteBtn, rolesListChk;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Workflow Email";

    public WorkflowEmailAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        descriptionField = By.xpath(propUISystemAdmin.getProperty("input_Description"));
        systemTaskSelect = By.xpath(propUISystemAdmin.getProperty("select_SystemTask"));
        systemMessageSelect = By.xpath(propUISystemAdmin.getProperty("select_SystemMessage"));
        //successMsg = By.xpath(propUISystemAdmin.getProperty("msg_Success"));
        rolesListChk = By.xpath(propUISystemAdmin.getProperty("chk_RolesList"));
        saveBtn = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISystemAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISystemAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

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


    public String saveWorkflowEmail(JSONObject data, String name) {
        String description, system_task, system_message;
        JSONArray user_groups;
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

            description = data.get("description").toString();
            findElement(descriptionField).sendKeys(description);
            jsonObj.put("description", description);

            system_task = data.get("system_task").toString();
            findElement(systemTaskSelect).sendKeys(system_task);
            jsonObj.put("system_task", system_task);

            system_message = data.get("system_message").toString();
            findElement(systemMessageSelect).sendKeys(system_message);
            jsonObj.put("system_message", system_message);

            List<WebElement> rolesListChkElements = findElements(rolesListChk);
            Boolean bGroup = false;
            for (WebElement rolesListChkElement:rolesListChkElements)
            {
                //System.out.println(rolesListChkElement.getAttribute("textContent"));
                String group = rolesListChkElement.getAttribute("textContent");

                user_groups = (JSONArray) data.get("user_groups");
                jsonObj.put("user_groups", user_groups);

                for (Iterator<String> iterator = user_groups.iterator(); iterator.hasNext(); user_groups.size()) {
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

    public Boolean checkWorkflowEmail(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(descriptionField).getAttribute("value").equals(name)) {
                //System.out.println("Filter Name is correct");

                try {
                    FileReader readFile = new FileReader(sPathToFile + sFileJson);
                    jsonMain = (JSONObject) parser.parse(readFile);
                    jsonObj = (JSONObject) jsonMain.get(name);
                } catch (ParseException e) {
                }

                // Compare field values with entry data
                try {
                    if (!new Select(findElement(systemTaskSelect)).getFirstSelectedOption().getText().equals(data.get("system_task").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!new Select(findElement(systemMessageSelect)).getFirstSelectedOption().getText().equals(data.get("system_message").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                // Compare checkbox values with entry data
                List<WebElement> rolesListChkElements = findElements(rolesListChk);
                Boolean bGroup = false;
                for (WebElement rolesListChkElement:rolesListChkElements)
                {
                    //System.out.println(rolesListChkElement.getAttribute("textContent"));
                    String group = rolesListChkElement.getAttribute("textContent");

                    JSONArray user_groups = (JSONArray) data.get("user_groups");

                    for (Iterator<String> iterator = user_groups.iterator(); iterator.hasNext(); user_groups.size()) {
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

                int emailAlertID = Integer.parseInt(jsonURLQuery.get("EmailAlertID").toString());

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
                return emailAlertID > 0;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public Boolean editWorkflowEmail(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = (JSONObject) jsonMain.get(name);
        By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);

            waitForElement(deleteBtn);

            try {
                if (!data.get("system_task_ch").toString().isEmpty()) {
                    findElement(systemTaskSelect).sendKeys(data.get("system_task_ch").toString());
                    jsonObj.put("system_task", data.get("system_task_ch").toString());
                }
            } catch (NullPointerException e) {}

            try {
                if (!data.get("system_message_ch").toString().isEmpty()) {
                    findElement(systemMessageSelect).sendKeys(data.get("system_message_ch").toString());
                    jsonObj.put("system_message", data.get("system_message_ch").toString());
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

            System.out.println(name + ": "+PAGE_NAME+" has been changed");
            return true;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean checkWorkflowEmailCh(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);

            waitForElement(deleteBtn);

            // Compare field values with changed data
            try {
                if (!new Select(findElement(systemTaskSelect)).getFirstSelectedOption().getText().equals(data.get("system_task_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(systemMessageSelect)).getFirstSelectedOption().getText().equals(data.get("system_message_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            // Compare checkbox values with entry data
            List<WebElement> rolesListChkElements = findElements(rolesListChk);
            Boolean bGroup = false;
            for (WebElement rolesListChkElement:rolesListChkElements)
            {
                String group = rolesListChkElement.getAttribute("textContent");

                JSONArray user_groups = (JSONArray) data.get("user_groups");

                for (Iterator<String> iterator = user_groups.iterator(); iterator.hasNext(); user_groups.size()) {
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

            System.out.println(name + ": " + PAGE_NAME + " changes have been checked");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean removeWorkflowEmail (String name) {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            waitForElement(deleteBtn);

            if (findElement(descriptionField).getAttribute("value").equals(name)) {

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
        String  sFilterId = JsonPath.read(obj, "$.['"+name+"'].url_query.EmailAlertID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?EmailAlertID="+sFilterId+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
