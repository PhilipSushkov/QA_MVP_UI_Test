package util;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 * Created by philipsushkov on 2016-12-08.
 */

public class Functions {
    private static Properties propUI;
    private static String currentDir;

    public Properties ConnectToPropUI(String sPathSharedUIMap) throws IOException {
        propUI = new Properties();
        currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
        propUI.load(new FileInputStream(currentDir + sPathSharedUIMap));
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

    public static String GetVersion(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sVersion = js.executeScript("return GetVersionNumber();").toString();
        return sVersion;
    }

    public static int GetResponseCode(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();
            huc.setRequestMethod("GET");
            //System.out.println(huc.getContentLength());
            //huc.connect();

            //System.out.println(Integer.toString(huc.getContentLength()));
            //System.out.println(Integer.toString(huc.getInputStream().available()));

            int iResponseCode = huc.getResponseCode();
            huc.disconnect();

            return iResponseCode;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 404;
        }
    }
}
