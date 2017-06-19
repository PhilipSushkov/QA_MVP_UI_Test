package pageobjects.ContentAdmin.PersonList;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;
import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by zacharyk on 2017-06-15.
 */
public class PersonAdd extends AbstractPageObject {
    private static By moduleTitle, firstNameField, lastNameField, suffixField, titleField, descriptionField, careerHighlightField;
    private static By departmentSelect, tagsField, photoPathField, thumbnailPathField, highResPathField, lowResPathField;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, publishBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Person";

    public PersonAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        firstNameField = By.xpath(propUIContentAdmin.getProperty("input_FirstName"));
        lastNameField = By.xpath(propUIContentAdmin.getProperty("input_LastName"));
        suffixField = By.xpath(propUIContentAdmin.getProperty("input_Suffix"));
        titleField = By.xpath(propUIContentAdmin.getProperty("input_Title"));
        descriptionField = By.xpath(propUIContentAdmin.getProperty("txtarea_Description"));
        careerHighlightField = By.xpath(propUIContentAdmin.getProperty("txtarea_CareerHighlight"));
        departmentSelect = By.xpath(propUIContentAdmin.getProperty("select_Department"));
        tagsField = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        photoPathField = By.xpath(propUIContentAdmin.getProperty("input_PhotoPath"));
        thumbnailPathField = By.xpath(propUIContentAdmin.getProperty("input_ThumbnailPath"));
        highResPathField = By.xpath(propUIContentAdmin.getProperty("input_HighResolutionPath"));
        lowResPathField = By.xpath(propUIContentAdmin.getProperty("input_LowResolutionPath"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUISiteAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUISiteAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUISiteAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_PersonList");
        sFileJson = propUIContentAdmin.getProperty("json_Person");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String savePerson(JSONObject data, String name) {
        String first_name, last_name, suffix, title, description, career_highlight, department, tags;
        String photo_path, thumbnail_path, high_resolution_path, low_resolution_path;
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

            first_name = data.get("first_name").toString();
            findElement(firstNameField).clear();
            findElement(firstNameField).sendKeys(first_name);
            jsonObj.put("first_name", first_name);

            last_name = data.get("last_name").toString();
            findElement(lastNameField).clear();
            findElement(lastNameField).sendKeys(last_name);
            jsonObj.put("last_name", last_name);

            suffix = data.get("suffix").toString();
            findElement(suffixField).clear();
            findElement(suffixField).sendKeys(suffix);
            jsonObj.put("suffix", suffix);

            title = data.get("title").toString();
            findElement(titleField).clear();
            findElement(titleField).sendKeys(title);
            jsonObj.put("title", title);

            description = data.get("description").toString();
            findElement(descriptionField).clear();
            findElement(descriptionField).sendKeys(description);
            jsonObj.put("description", description);

            career_highlight = data.get("career_highlight").toString();
            findElement(careerHighlightField).clear();
            findElement(careerHighlightField).sendKeys(career_highlight);
            jsonObj.put("career_highlight", career_highlight);

            department = data.get("department").toString();
            findElement(departmentSelect).sendKeys(department);
            jsonObj.put("department", department);

            tags = data.get("tags").toString();
            findElement(tagsField).clear();
            findElement(tagsField).sendKeys(tags);
            jsonObj.put("tags", tags);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc;

            photo_path = data.get("photo_path").toString();
            elemSrc = driver.findElement(photoPathField);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+photo_path);
            jsonObj.put("photo_path", photo_path);

            thumbnail_path = data.get("thumbnail_path").toString();
            elemSrc = driver.findElement(thumbnailPathField);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+thumbnail_path);
            jsonObj.put("thumbnail_path", thumbnail_path);

            high_resolution_path = data.get("high_resolution_path").toString();
            elemSrc = driver.findElement(highResPathField);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+high_resolution_path);
            jsonObj.put("high_resolution_path", high_resolution_path);

            low_resolution_path = data.get("low_resolution_path").toString();
            elemSrc = driver.findElement(lowResPathField);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+low_resolution_path);
            jsonObj.put("low_resolution_path", low_resolution_path);

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
            waitForElement(successMsg);

            jsonObj.put("workflow_state", WorkflowState.IN_PROGRESS.state());

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

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String saveAndSubmitPerson(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='" + data.get("first_name").toString() + " " + data.get("last_name").toString() +
                                "')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Persons')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            findElement(departmentSelect).sendKeys(data.get("department").toString());

            //String pageUrl = getPageUrl(jsonMain, name);
            //driver.get(pageUrl);
            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            findElement(departmentSelect).sendKeys(data.get("department").toString());

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            // Save Link To Page Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" has been sumbitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkPerson(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!findElement(firstNameField).getAttribute("value").equals(data.get("first_name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            if (!checkUnchangedFields(data)) return false;

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishPerson(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.LIVE.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println(name+ ": New "+PAGE_NAME+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitPerson(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(saveAndSubmitBtn);

            try {
                if (!data.get("first_name_ch").toString().isEmpty()) {
                    findElement(firstNameField).clear();
                    findElement(firstNameField).sendKeys(data.get("first_name_ch").toString());
                    jsonObj.put("first_name", data.get("first_name_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            try {
                // Save Active checkbox
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
            } catch (NullPointerException e) {
            }

            try {
                findElement(commentsTxt).clear();
                findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
            } catch (NullPointerException e) {
            }

            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(workflowStateSpan);

            System.out.println(name+ ": New "+PAGE_NAME+" changes have been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String revertToLivePerson(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(revertBtn);

            if (jsonObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                findElement(revertBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                jsonObj.put("workflow_state", WorkflowState.LIVE.state());
                jsonObj.put("deleted", "false");

                jsonMain.put(name, jsonObj);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(workflowStateSpan);

                System.out.println(name+ ": "+PAGE_NAME+" has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkLookupCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!findElement(firstNameField).getAttribute("value").equals(data.get("first_name_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            if (!checkUnchangedFields(data)) return false;

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedPerson(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+PAGE_NAME);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removePerson(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+PAGE_NAME+" removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);

                jsonMain.remove(name);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(name+ ": New "+PAGE_NAME+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Boolean checkUnchangedFields(JSONObject data) {
        try {
            if (!findElement(lastNameField).getAttribute("value").equals(data.get("last_name").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(suffixField).getAttribute("value").equals(data.get("suffix").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(titleField).getAttribute("value").equals(data.get("title").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(descriptionField).getText().equals(data.get("description").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(careerHighlightField).getText().equals(data.get("career_highlight").toString())) {
                System.out.println(findElement(careerHighlightField).getText() + "!=" + data.get("career_highlight"));
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            Select departmentDropdown = new Select(findElement(departmentSelect));
            if (!departmentDropdown.getFirstSelectedOption().getText().equals(data.get("department").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(tagsField).getAttribute("value").equals(data.get("tags").toString()+" ")) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(photoPathField).getAttribute("value").equals("files/" + data.get("photo_path").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(thumbnailPathField).getAttribute("value").equals("files/" + data.get("thumbnail_path").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(highResPathField).getAttribute("value").equals("files/" + data.get("high_resolution_path").toString())) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        try {
            if (!findElement(lowResPathField).getAttribute("value").equals("files/" + data.get("low_resolution_path").toString())) {
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
        return true;
    }

    public String getPageUrl(JSONObject obj, String name) {
        String  sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String  sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String  sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
