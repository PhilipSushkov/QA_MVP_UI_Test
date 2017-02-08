package pageobjects.SystemAdmin.AlertFilterList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-02-07.
 */
public class AlertFilterAdd extends AbstractPageObject {
    private static By moduleTitle, filterNameInput, entityTypeSelect, mailingListSelect;
    private static By addNewLink, saveBtn, cancelBtn;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;

    public AlertFilterAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        filterNameInput = By.xpath(propUISystemAdmin.getProperty("input_FilterName"));
        entityTypeSelect = By.xpath(propUISystemAdmin.getProperty("select_EntityType"));
        mailingListSelect = By.xpath(propUISystemAdmin.getProperty("select_MailingList"));
        saveBtn = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISystemAdmin.getProperty("btn_Cancel"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_AlertFilterList");
        sFileJson = propUISystemAdmin.getProperty("json_AlertFilters");
    }


    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }


    public String saveAlertFilter(JSONObject data, String name) {
        String entity_type, mailing_list;
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

            findElement(filterNameInput).sendKeys(name);
            jsonObj.put("filter_name", name);

            entity_type = data.get("entity_type").toString();
            findElement(entityTypeSelect).sendKeys(entity_type);
            jsonObj.put("entity_type", entity_type);

            Select mailingList = new Select(findElement(mailingListSelect));
            mailingList.selectByIndex(0);
            mailing_list = new Select(findElement(mailingListSelect)).getFirstSelectedOption().getText();
            jsonObj.put("mailing_list", mailing_list);

            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            jsonMain.put(name, jsonObj);

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
            waitForElement(moduleTitle);

            return findElement(moduleTitle).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkAlertFilter(JSONObject data, String name) {
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        By editBtn = By.xpath("//span[contains(text(), '" + name + "')]/parent::td/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(filterNameInput).getAttribute("value").equals(name)) {
                System.out.println("Filter Name is correct");

                try {
                    FileReader readFile = new FileReader(sPathToFile + sFileJson);
                    jsonMain = (JSONObject) parser.parse(readFile);
                    jsonObj = (JSONObject) jsonMain.get(name);
                } catch (ParseException e) {
                }

                URL url = new URL(getUrl());
                String[] params = url.getQuery().split("&");
                JSONObject jsonURLQuery = new JSONObject();
                for (String param:params) {
                    jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
                }
                jsonObj.put("url_query", jsonURLQuery);

                int filterID = Integer.parseInt(jsonURLQuery.get("FilterId").toString());

                try {
                    FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                    writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                    writeFile.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return filterID > 0;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
