package pageobjects.api;

import org.openqa.selenium.By;

import java.io.*;
import java.util.LinkedList;

public class GoldPriceCheck extends GetGoldValues {
    public static String yahooURL = "https://finance.yahoo.com/quote/XAUUSD=X/",
                         tradingViewURL = "https://www.tradingview.com/symbols/FX-XAUUSD/",
                         q4APIURL = "http://ir.euroinvestor.com/ServiceEngine/api/xml/reply/RequestStockDataBundle?apiversion=1&solutionID=3716&customerKey=Gold&instrumentTypes=Listing&CustomFormat=EmperorDesign";
    public static By yahooSpotPricePath = By.xpath("//*[@id=\"quote-header-info\"]/div[3]/div[1]/div/span[1]"),
                     yahooOpenPricePath = By.xpath("//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[2]/td[2]/span"),
                     yahooClosePricePath = By.xpath("//*[@id=\"quote-summary\"]/div[1]/table/tbody/tr[1]/td[2]/span"),
                     tradingViewSpotPricePath = By.xpath("//*[@id=\"anchor-page-1\"]/div/div[2]/div/div[1]/div[1]/span[1]"),
                     tradingViewOpenPricePath = By.xpath("//*[@id=\"anchor-page-1\"]/div/div[2]/div/div[2]/div[2]/span"),
                     tradingViewClosePricePath = By.xpath("//*[@id=\"anchor-page-1\"]/div/div[2]/div/div[2]/div[1]/span");
    public static String fileName = System.getProperty("user.dir") + "/src/test/java/util/tempEmail.txt";

    public static boolean checkAPIField (String compareSite, String type, double delta) throws Exception {
        boolean testResult=false;
        String message;
        double valueAPI = getAPIValue(q4APIURL, type),
               valueExternal = getExternalValue(compareSite, type),
               lowerBound = valueExternal*(1-delta),
               upperBound = valueExternal*(1+delta);
        System.out.println("Value API: " + valueAPI);
        System.out.println("Value External: " + valueExternal);
        try {
            if (valueAPI == -1 || valueExternal == -1) {
                if (valueAPI == -1)
                    message = "ERROR! valueAPI Method Error";
                else
                    message = "ERROR! valueExternal Method Error";
                testResult = false;
            } else if (valueAPI == valueExternal) {
                message = "Within Delta -> PASS";
                testResult = true;
            } else if (valueAPI > 0 && valueAPI > lowerBound && valueAPI < upperBound) {
                message = "Within Delta -> PASS";
                testResult = true;
            } else if (valueAPI < 0 && valueAPI < lowerBound && valueAPI > upperBound) {
                message = "Within Delta -> PASS";
                testResult = true;
            } else {
                message = "Outside Delta -> FAIL";
                testResult = false;
            }
        }
        catch (NumberFormatException e) {
            message = "Problem with test case, must investigate -> FAIL";
            testResult = false;
        }
        System.out.println(message);
        appendFile("Using " + compareSite + ", Testing " + type + " " + message);
        return testResult;
    }

    public static double getExternalValue(String compareSite, String type) {
        double value;
        switch (compareSite) {
            case "YAHOO":
                value = getWebValues(compareSite, yahooURL, type, yahooSpotPricePath, yahooOpenPricePath, yahooClosePricePath);
                break;
            case "TRADINGVIEW":
                value = getWebValues(compareSite, tradingViewURL, type, tradingViewSpotPricePath, tradingViewOpenPricePath, tradingViewClosePricePath);
                break;
            default:
                System.out.println("ERROR! Invalid Site");
                value = -1;
                break;
        }
        return value;
    }

    public static void createFile() throws Exception {
        try {
            FileWriter fileCreate = new FileWriter(fileName);
            BufferedWriter bufferedCreate = new BufferedWriter(fileCreate);
            bufferedCreate.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendFile(String result) throws Exception {
        try {
            FileWriter fileAppend = new FileWriter(fileName, true);
            BufferedWriter bufferedAppend = new BufferedWriter(fileAppend);
            bufferedAppend.write(result);
            bufferedAppend.newLine();
            bufferedAppend.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LinkedList<String> compileResult() throws Exception {
        LinkedList<String> results = new LinkedList<String>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                results.add(line);
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
