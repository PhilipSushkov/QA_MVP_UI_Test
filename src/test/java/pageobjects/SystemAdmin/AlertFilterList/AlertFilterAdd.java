package pageobjects.SystemAdmin.AlertFilterList;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-02-07.
 */
public class AlertFilterAdd extends AbstractPageObject {
    private static By moduleTitle, filterNameInput, entityTypeSelect, mailingListSelect;
    private static By addNewLink, saveBtn, cancelBtn, deleteBtn, sendEmailIsChk, activeIsChk;
    private static By filterTypeSelect, includedTitleItems, includedBodyItems, excludedTitleItems, excludedBodyItems;
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
        deleteBtn = By.xpath(propUISystemAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));
        sendEmailIsChk = By.xpath(propUISystemAdmin.getProperty("chk_SendEmail"));
        activeIsChk = By.xpath(propUISystemAdmin.getProperty("chk_IsActive"));

        filterTypeSelect = By.xpath(propUISystemAdmin.getProperty("select_FilterType"));
        includedTitleItems = By.xpath(propUISystemAdmin.getProperty("txtarea_IncludedTitleItems"));
        includedBodyItems = By.xpath(propUISystemAdmin.getProperty("txtarea_IncludedBodyItems"));
        excludedTitleItems = By.xpath(propUISystemAdmin.getProperty("txtarea_ExcludedTitleItems"));
        excludedBodyItems = By.xpath(propUISystemAdmin.getProperty("txtarea_ExcludedBodyItems"));

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

            // Save Filter Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            try {
                // Save Active checkbox
                if (Boolean.parseBoolean(data.get("active").toString())) {
                    if (!Boolean.parseBoolean(findElement(activeIsChk).getAttribute("checked"))) {
                        findElement(activeIsChk).click();
                        jsonObj.put("active", true);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(activeIsChk).getAttribute("checked"))) {
                    } else {
                        findElement(activeIsChk).click();
                        jsonObj.put("active", false);
                    }
                }
            } catch (NullPointerException e) {
            }


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

            System.out.println(name + ": Filter Alert has been created");
            return findElement(moduleTitle).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkAlertFilter(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        By editBtn = By.xpath("//span[(text()='" + name + "')]/parent::td/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            if (findElement(filterNameInput).getAttribute("value").equals(name)) {
                //System.out.println("Filter Name is correct");

                try {
                    FileReader readFile = new FileReader(sPathToFile + sFileJson);
                    jsonMain = (JSONObject) parser.parse(readFile);
                    jsonObj = (JSONObject) jsonMain.get(name);
                } catch (ParseException e) {
                }

                // Compare field values with entry data
                try {
                    if (!new Select(findElement(entityTypeSelect)).getFirstSelectedOption().getText().equals(data.get("entity_type").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(activeIsChk).getAttribute("checked").equals(data.get("active").toString())) {
                        return false;
                    }
                } catch (NullPointerException e) {
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

                System.out.println(name + ": Filter Alert has been checked");
                return filterID > 0;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public Boolean editAlertFilter(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = (JSONObject) jsonMain.get(name);
        By editBtn = By.xpath("//span[(text()='" + name + "')]/parent::td/parent::tr/td/input[contains(@id, 'btnEdit')]");

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
                if (!data.get("active_ch").toString().isEmpty()) {
                    if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(activeIsChk).getAttribute("checked"))) {
                            findElement(activeIsChk).click();
                            jsonObj.put("active", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(activeIsChk).getAttribute("checked"))) {
                        } else {
                            findElement(activeIsChk).click();
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

            System.out.println(name + ": Filter Alert has been changed");
            return true;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Boolean checkAlertFilterCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

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


            // Compare field values with entry data
            try {
                if (!findElement(filterNameInput).getAttribute("value").equals(data.get("filter_name_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(entityTypeSelect)).getFirstSelectedOption().getText().equals(data.get("entity_type_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeIsChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }


            System.out.println(name + ": Filter Alert changes have been checked");
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    public Boolean removeAlertFilter (String name) {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//span[(text()='" + name + "')]/parent::td/parent::tr/td/input[contains(@id, 'btnEdit')]");

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

            if (findElement(filterNameInput).getAttribute("value").equals(name)) {

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

                    System.out.println(name + ": Filter Alert has been removed");
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

    public Boolean setContentFilters(String filterType, String filterTitle, String filterBody) {

        try {

            findElement(filterTypeSelect).sendKeys(filterType);

            WebElement titleField;
            WebElement bodyField;

            if (filterType.equals("exclude")) {
                titleField = findElement(excludedTitleItems);
                bodyField = findElement(excludedBodyItems);
            } else if (filterType.equals("include")) {
                titleField = findElement(includedTitleItems);
                bodyField = findElement(includedBodyItems);
            } else return false;

            titleField.clear();
            titleField.sendKeys(filterTitle);
            bodyField.clear();
            bodyField.sendKeys(filterBody);

        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Boolean enableSendToList(String mailingList) {

        try {

            findElement(mailingListSelect).sendKeys(mailingList);

            WebElement activeChk = findElement(activeIsChk);
            WebElement sendEmailChk = findElement(sendEmailIsChk);

            if (activeChk.getAttribute("checked") == null) {
                activeChk.click();
            }
            if (sendEmailChk.getAttribute("checked") == null) {
                sendEmailChk.click();
            }

            findElement(saveBtn).click();
            return  true;

        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        }

        return false;

    }

    public String getPageUrl (JSONObject obj, String name) {
        String  sFilterId = JsonPath.read(obj, "$.['"+name+"'].url_query.FilterId");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?FilterId="+sFilterId+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }

}
