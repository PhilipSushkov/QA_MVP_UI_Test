package pageobjects.PageAdmin;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import java.io.*;
import java.net.URL;
import java.util.List;

import util.Functions;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-11.
 */

public class PageAdminList extends AbstractPageObject {
    private static By moduleTitle, contentInnerWrap, dataGridTable, dataGridItemBorder, editPageImg;
    private static By backBtn, sectionTitleInput, yourPageUrlLabel, seoNameInput, pageTitleInput, parentSectionSelect;
    private static By descriptionTextarea, domainSelect, switchBtn, pageTypeExternalRd, globalModuleSetLbl;
    private static By pageTypeInternalRd, pageLayoutSelect, externalURLInput, activeChk, globalModuleSetChk;
    private static By moduleInstancesSpan, sectionTitleSpan, pageNameLbl, moduleNameLbl, editModuleImg, moduleDefinitionSelect;
    private static By workflowStateSpan, showNavChk, openNewWindChk;
    private static String sPathToFile, sDataFilePagesJson, sDataFileModulesJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 1500;

    public PageAdminList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        contentInnerWrap = By.xpath(propUIPageAdmin.getProperty("span_ContentInnerWrap"));
        dataGridTable = By.xpath(propUIPageAdmin.getProperty("table_DataGrid"));
        dataGridItemBorder = By.xpath(propUIPageAdmin.getProperty("table_DataGridItemBorder"));
        editPageImg = By.xpath(propUIPageAdmin.getProperty("img_EditPage"));
        editModuleImg = By.xpath(propUIPageAdmin.getProperty("img_EditModule"));
        pageNameLbl = By.xpath(propUIPageAdmin.getProperty("lbl_PageName"));
        moduleNameLbl = By.xpath(propUIPageAdmin.getProperty("lbl_ModuleName"));
        backBtn = By.xpath(propUIPageAdmin.getProperty("btn_Back"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        yourPageUrlLabel = By.xpath(propUIPageAdmin.getProperty("span_YourPageUrl"));
        seoNameInput = By.xpath(propUIPageAdmin.getProperty("input_SeoName"));
        pageTitleInput = By.xpath(propUIPageAdmin.getProperty("input_PageTitle"));
        pageLayoutSelect = By.xpath(propUIPageAdmin.getProperty("select_PageLayout"));
        pageTypeInternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeInternal"));
        pageTypeExternalRd = By.xpath(propUIPageAdmin.getProperty("rd_PageTypeExternal"));
        descriptionTextarea = By.xpath(propUIPageAdmin.getProperty("txtarea_Description"));
        domainSelect = By.xpath(propUIPageAdmin.getProperty("select_Domain"));
        switchBtn = By.xpath(propUIPageAdmin.getProperty("btn_Switch"));
        externalURLInput = By.xpath(propUIPageAdmin.getProperty("input_ExternalURL"));
        activeChk = By.xpath(propUIPageAdmin.getProperty("chk_Active"));
        globalModuleSetChk = By.xpath(propUIPageAdmin.getProperty("chk_GlobalModuleSet"));
        globalModuleSetLbl = By.xpath(propUIPageAdmin.getProperty("lbl_GlobalModuleSet"));
        moduleInstancesSpan = By.xpath(propUIPageAdmin.getProperty("span_ModuleInstances"));
        sectionTitleSpan = By.xpath(propUIPageAdmin.getProperty("span_SectionTitle"));
        moduleDefinitionSelect = By.xpath(propUIPageAdmin.getProperty("select_ModuleDefinition"));
        workflowStateSpan = By.xpath(propUIPageAdmin.getProperty("select_WorkflowState"));
        parentSectionSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentSection"));
        showNavChk = By.xpath(propUIPageAdmin.getProperty("chk_ShowInNav"));
        openNewWindChk = By.xpath(propUIPageAdmin.getProperty("chk_OpenInNewWindow"));

        sPathToFile = System.getProperty("user.dir") + propUIPageAdmin.getProperty("dataPath_PageAdmin");
        sDataFilePagesJson = propUIPageAdmin.getProperty("json_PagesProp");
        sDataFileModulesJson = propUIPageAdmin.getProperty("json_ModulesProp");

        parser = new JSONParser();
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Boolean getPageItems() {
        boolean pageItems = false;
        List<WebElement> elements;
        String[][] sa1;
        String[] sa2 = null;
        int i;

        try {
            waitForElement(contentInnerWrap);
            elements = findElements(contentInnerWrap);

            if (elements.size() > 0 ) {
                sa1 = new String[elements.size()+1][2];
                sa2 = new String[elements.size()];
                i = 0;

                sa1[i][0] = "ID";
                sa1[i][1] = "Page Name";

                pageItems = true;

                for (WebElement element:elements)
                {
                    i++;
                    sa1[i][0] = Integer.toString(i);
                    sa1[i][1] = element.getText();
                    sa2[i-1] = element.getText();
                }
            }
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        Functions.ClearJSONfile(sPathToFile + sDataFilePagesJson);
        Functions.WriteArrayToJSON(sa2, sPathToFile + sDataFilePagesJson, "page_names");
        return pageItems;
    }

    public Boolean clickPageItems() throws InterruptedException {
        boolean pageNames = true;
        By innerWrapPage;
        String[] sa2;

        sa2 = Functions.ReadArrayFromJSON(sPathToFile + sDataFilePagesJson, "page_names");
        Functions.ClearJSONfile(sPathToFile + sDataFileModulesJson);

        try {
            for (int pageSection=0; pageSection<sa2.length; pageSection++) {
                innerWrapPage = By.xpath("//div[contains(@id, 'divContent')]//span[contains(@class, 'innerWrap')][(text()=\""+sa2[pageSection]+"\")]/parent::span/parent::a");
                waitForElement(innerWrapPage);
                findElement(innerWrapPage).click();

                waitForElement(dataGridTable);
                waitForElement(editPageImg);

                // Starts pages loop
                List<WebElement> editPages = findElements(editPageImg);
                for (int pageNum=0; pageNum<editPages.size(); pageNum++) {
                    findElements(editPageImg).get(pageNum).click();

                    waitForElement(backBtn);
                    Thread.sleep(DEFAULT_PAUSE);

                    savePageToJSON(sPathToFile, sDataFilePagesJson, sDataFileModulesJson);

                    findElement(backBtn).click();
                    waitForElement(dataGridItemBorder);
                }
            }

            return pageNames;

        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getUrlPage(String searchPhrase) throws InterruptedException {
        JSONParser parser = new JSONParser();
        String urlPage = null;

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
            urlPage = JsonPath.read(jsonObject, "$."+searchPhrase+".your_page_url");
            String itemID = JsonPath.read(jsonObject, "$."+searchPhrase+".url_query.ItemID");
            //System.out.println(itemID);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(urlPage);

        return urlPage;
    }

    public JSONObject savePageToJSON (String sPathToFile, String sDataFilePagesJson, String sDataFileModulesJson) throws InterruptedException {
        String itemID=null, sectionTitle, itemIDModule=null, your_page_url;
        int j;
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectModule = new JSONObject();
        JSONObject mmjson = new JSONObject();

        // Add values to JSON file
        try {
            try {
                jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFilePagesJson));
                jsonObjectModule = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileModulesJson));
            } catch (ParseException e) {
            }

            URL pageURL = new URL(getUrl());

            String[] params = pageURL.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();

            for (String param : params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);

                if (param.split("=")[0].equals("ItemID")) {
                    itemID = param.split("=")[1];
                }
            }

            mmjson.put("url_query", jsonURLQuery);

            sectionTitle = findElement(sectionTitleInput).getAttribute("value");
            mmjson.put("section_title", sectionTitle);
            your_page_url = findElement(yourPageUrlLabel).getText() + findElement(seoNameInput).getAttribute("value");
            mmjson.put("your_page_url", your_page_url);
            mmjson.put("page_title", findElement(pageTitleInput).getAttribute("value"));

            if (Boolean.parseBoolean(findElement(pageTypeInternalRd).getAttribute("checked"))) {
                mmjson.put("page_type", "Internal");
                mmjson.put("page_layout", new Select(driver.findElement(pageLayoutSelect)).getFirstSelectedOption().getText());
                mmjson.put("description", findElement(descriptionTextarea).getText());
                mmjson.put("domain", new Select(driver.findElement(domainSelect)).getFirstSelectedOption().getText());

                findElement(switchBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

            } else if (Boolean.parseBoolean(findElement(pageTypeExternalRd).getAttribute("checked"))) {
                mmjson.put("page_type", "External");
                mmjson.put("external_url", findElement(externalURLInput).getAttribute("value"));
            }

            mmjson.put("parent_section", new Select(driver.findElement(parentSectionSelect)).getFirstSelectedOption().getText());
            mmjson.put("active", findElement(activeChk).getAttribute("checked"));
            mmjson.put("show_in_navigation", Boolean.parseBoolean(findElement(showNavChk).getAttribute("checked")));
            mmjson.put("open_in_new_window", Boolean.parseBoolean(findElement(openNewWindChk).getAttribute("checked")));
            mmjson.put("workflow_state", findElement(workflowStateSpan).getText());

            // Save Global Module Settings list
            List<WebElement> globalModuleInputs;
            List<WebElement> globalModuleLabels;
            JSONArray globalModuleSettings = new JSONArray();
            j = 0;
            globalModuleInputs = findElements(globalModuleSetChk);
            globalModuleLabels = findElements(globalModuleSetLbl);
            for (WebElement globalModuleInput : globalModuleInputs) {
                if (Boolean.parseBoolean(globalModuleInput.getAttribute("checked"))) {
                    globalModuleSettings.add(globalModuleLabels.get(j).getText());
                }
                j++;
            }
            mmjson.put("global_module_settings", globalModuleSettings);

            // Save Modules list
            List<WebElement> moduleSpans;
            JSONArray moduleList = new JSONArray();
            j = 0;
            moduleSpans = findElements(moduleInstancesSpan);
            for (WebElement moduleSpan : moduleSpans) {
                moduleList.add(moduleSpan.getText());
                j++;
            }
            mmjson.put("modules", moduleList);

            // ----------------------------------------------------
            // Open Module pages
            List<WebElement> modulePages;
            modulePages = findElements(moduleInstancesSpan);

            for (int moduleNum=0; moduleNum<modulePages.size(); moduleNum++) {
                findElements(editModuleImg).get(moduleNum).click();
                waitForElement(backBtn);

                JSONObject pageParameters = new JSONObject();
                pageParameters.put("section_title", sectionTitle);
                pageParameters.put("page_url", your_page_url);

                JSONObject mmjsonModule = new JSONObject();
                mmjsonModule.put(itemID, pageParameters);

                URL pageURLModule = new URL(getUrl());

                String[] paramModules = pageURLModule.getQuery().split("&");
                JSONObject jsonURLQueryModule = new JSONObject();

                for (String paramparamModule : paramModules) {
                    jsonURLQueryModule.put(paramparamModule.split("=")[0], paramparamModule.split("=")[1]);

                    if (paramparamModule.split("=")[0].equals("ItemID")) {
                        itemIDModule = paramparamModule.split("=")[1];
                    }
                }

                mmjsonModule.put("url_query", jsonURLQueryModule);
                mmjsonModule.put(new Select(driver.findElement(moduleDefinitionSelect)).getFirstSelectedOption().getText(), your_page_url);
                mmjsonModule.put("active", findElement(activeChk).getAttribute("checked"));
                mmjsonModule.put("workflow_state", findElement(workflowStateSpan).getText());

                jsonObjectModule.put(itemIDModule, mmjsonModule);

                findElement(backBtn).click();

                FileWriter fileModule = new FileWriter(sPathToFile + sDataFileModulesJson);
                fileModule.write(jsonObjectModule.toJSONString().replace("\\", ""));
                fileModule.flush();
                fileModule.close();
            }

            // ----------------------------------------------------

            // Save Sub Pages list
            List<WebElement> subPagesSpans;
            JSONArray subPagesList = new JSONArray();
            j = 0;
            subPagesSpans = findElements(sectionTitleSpan);
            for (WebElement subPagesSpan : subPagesSpans) {
                subPagesList.add(subPagesSpan.getText());
                j++;
            }
            mmjson.put("subpages", subPagesList);
            jsonObject.put(sectionTitle, mmjson);

            FileWriter file = new FileWriter(sPathToFile + sDataFilePagesJson);
            file.write(jsonObject.toJSONString().replace("\\", ""));
            file.flush();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
