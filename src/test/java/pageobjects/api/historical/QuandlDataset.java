package pageobjects.api.historical;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by dannyl on 2017-05-19.
 */

/**
 Class to store the different types of data that are retrieved from the Quandl api
 For documentation on how end of day stock info is displaying in the JSON file go to
 https://www.quandl.com/data/EOD-End-of-Day-US-Stock-Prices/documentation/documentation
 */
public class QuandlDataset {
    private ArrayList<String> closingPrices = new ArrayList<String>();
    private ArrayList<String> closingPriceDate = new ArrayList<>();
    private ArrayList<String> openPrices = new ArrayList<>();
    private ArrayList<String> highPrices = new ArrayList<>();
    private ArrayList<String> lowPrices = new ArrayList<>();
    private ArrayList<String> volume = new ArrayList<>();
    private String rawData;

    JSONParser parser = new JSONParser();
    // Fills the data-types initialized above with it's corresponding data
    public QuandlDataset(String input, String type){
        try {

            JSONObject json = (JSONObject) parser.parse(input);
            // Create a JSONObject containing all the stock information (To understand better reference the json file that the quandl api outputs)
            JSONObject dataSet = (JSONObject) json.get("dataset_data");
            rawData = dataSet.toString();
            // Parses through the JSONObject json for all the values under the key "data"
            JSONArray tempClosingPrice = (JSONArray) dataSet.get("data");
            Iterator<JSONArray> iterator = tempClosingPrice.iterator();

            // Store the closing prices taken from tempClosingPrice into closingPrices and
            //      store the date of each closing price into closingPriceDate
            while (iterator.hasNext()){
                JSONArray handler = iterator.next();
                closingPriceDate.add(handler.get(0).toString());
                openPrices.add(handler.get(1).toString());
                highPrices.add(handler.get(2).toString());
                lowPrices.add(handler.get(3).toString());
                closingPrices.add(handler.get(4).toString());
                volume.add(handler.get(5).toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Method to return ArrayList of closingPrices
    public ArrayList<String> getClosingPrices(){
        return closingPrices;
    }

    public ArrayList<String> getClosingPriceDates(){
        return closingPriceDate;
    }

    public String getRawData(){
        return rawData;
    }

    public ArrayList<String> getHighPrices(){ return highPrices; }

    public ArrayList<String> getOpenPrices(){ return openPrices; }

    public ArrayList<String> getLowPrices(){ return lowPrices; }

    public ArrayList<String> getVolume() { return volume; }

    // Note these functions only work when there is only one quote

}
