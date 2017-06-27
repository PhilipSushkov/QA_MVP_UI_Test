package specs.Modules.util;

import org.apache.xpath.operations.Bool;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import static specs.AbstractSpec.propUIModulesFeed;

/**
 * Created by zacharyk on 2017-06-21.
 */
public class ModuleFunctions {

    public static Boolean checkExpectedValue(WebDriver driver, String expected, JSONObject module, String jsonModulePath, Properties propUIFile) {

        String type, elementPath, expectedValue, attribute, valueName, moduleCompare;

        String modulePath = module.get("module_path").toString();
        String moduleName = module.get("module_title").toString();

        String[] data = expected.split(";");
        type = data[0];
        elementPath = data[1];
        System.out.println(elementPath);
        By element = By.xpath(modulePath+propUIFile.getProperty(elementPath));

        switch (type) {
            case "text":

                expectedValue = data[2];
                return checkText(driver, element, expectedValue);

            case "attribute":

                attribute  = data[2];
                expectedValue = data[3];
                return checkAttribute(driver, element, attribute, expectedValue);

            case "element":

                Boolean expectPresent = Boolean.valueOf(data[2]);
                return checkElementPresent(driver, element, expectPresent);

            case "save":

                valueName = data[2];
                return saveValue(driver, element, valueName, jsonModulePath, moduleName);

            case "compare":

                valueName = data[2];
                moduleCompare = data[3];
                Boolean compareEquals = Boolean.valueOf(data[4]);
                return compareValue(driver, element, valueName, jsonModulePath, moduleCompare, compareEquals);

            case "text_exists":

                Boolean expectText = Boolean.valueOf(data[2]);
                return checkTextExists(driver, element, expectText);

            default: return false;
        }
    }

    private static Boolean checkText(WebDriver driver, By element, String expected) {
        return driver.findElement(element).getText().contains(expected);
    }

    private static Boolean checkAttribute(WebDriver driver, By element, String attribute, String expected) {
        return driver.findElement(element).getAttribute(attribute).contains(expected);
    }

    private static Boolean checkElementPresent(WebDriver driver, By element, Boolean expectPresent) {
        return driver.findElements(element).isEmpty() != expectPresent;
    }

    private static Boolean checkTextExists(WebDriver driver, By element, Boolean expectText) {
        return driver.findElement(element).getText().isEmpty() != expectText;
    }

    private static Boolean saveValue(WebDriver driver, By element, String valueName, String jsonModulePath, String moduleName) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(jsonModulePath));

            JSONObject moduleObj = (JSONObject) jsonObj.get(moduleName);

            moduleObj.put(valueName, driver.findElement(element).getText());
            jsonObj.put(moduleName, moduleObj);

            FileWriter file = new FileWriter(jsonModulePath);
            file.write(jsonObj.toJSONString().replace("\\", ""));
            file.flush();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private static Boolean compareValue(WebDriver driver, By element, String valueName, String jsonModulePath, String moduleCompare, Boolean compareEquals) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObj = (JSONObject) parser.parse(new FileReader(jsonModulePath));
            JSONObject moduleObj = (JSONObject) jsonObj.get(moduleCompare);

            if (compareEquals) {
                return driver.findElement(element).getText().equals(moduleObj.get(valueName).toString());
            } else {
                return !driver.findElement(element).getText().equals(moduleObj.get(valueName).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
