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
import pageobjects.PageAdmin.WorkflowState;

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

        return true;
    }
}
