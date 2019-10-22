package util;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import java.io.*;
import java.util.*;

/**
 * Created by philipsushkov on 2016-12-08.
 */

public class Functions {

    public static Properties ConnectToPropUI(String sPathSharedUIMap) {
        Properties propUI = null;
        String currentDir = null;

        try {
            propUI = new Properties();
            currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
            propUI.load(new FileInputStream(currentDir + sPathSharedUIMap));
        } catch (IOException e) {
            System.out.println("File "+currentDir + sPathSharedUIMap+" didn't load properly!");
        }

        return propUI;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void WriteArrayToJSON(String[] args, String sPathToFile, String sArrayName) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            obj = (JSONObject) parser.parse(new FileReader(sPathToFile));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<args.length; i++) {
            list.add(args[i]);
        }

        obj.put(sArrayName, list);

        try {
            FileWriter file = new FileWriter(sPathToFile);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] ReadArrayFromJSON(String sPathToFile, String sArrayName) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(sPathToFile));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray items = (JSONArray) jsonObject.get(sArrayName);
            String temp[] = new String[items.size()];

            int i = 0;
            for (Iterator<String> iterator = items.iterator(); iterator.hasNext(); i++) {
                temp[i] = iterator.next();
            }

            return temp;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Clear JSON file
    public static void ClearJSONfile(String sPathToFile) {
        try {
            FileWriter fileClear = new FileWriter(sPathToFile);
            fileClear.write("");
            fileClear.flush();
            fileClear.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray RemoveArrayItem(JSONArray jsonArr, String item) {
        for (int i=0; i<jsonArr.size(); i++) {
            if (jsonArr.get(i).toString().contains(item)) {
                jsonArr.remove(i);
                i--;
            }
        }
        return jsonArr;
    }

    public static String UrlAddSlash(String searchTerm, String sSlash, String sHttp) {
        searchTerm = searchTerm.trim();

        String sFirstChar = searchTerm.substring(0, 7);

        if (!sFirstChar.equals(sHttp)) {
            searchTerm = sHttp + searchTerm;
        }

        String sLastChar = searchTerm.substring(searchTerm.length()-1, searchTerm.length());
        if (sLastChar.equals(sSlash)) {
            return searchTerm;
        } else {
            searchTerm = searchTerm + sSlash;
            return searchTerm;
        }

    }

    public static String takeScreenshot(WebDriver driver, String sShotName, String sPageName) {
        String path = null;

        try {
            // Take screenshot and save it in source object
            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            // Define path where Screenshot will be saved
            path = System.getProperty("user.dir") + "/src/test/java/specs/ImageComparison/ScreenShots/" + sPageName + "/" + sShotName + ".png";
            //System.out.println(path);

            //Copy the source file at the screenshot path
            FileUtils.copyFile(source,  new File(path));
            System.out.println("Screenshot " + sShotName + " captured");
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot:" + e.getMessage());
        } catch (WebDriverException wde) {
            System.out.println("Failed to capture screenshot:" + wde.getMessage());
        }

        return path;
    }

    public static void cleanTextFields(List<WebElement> fields) {
        for (WebElement e : fields) {
            e.clear();
        }
    }

    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

}
