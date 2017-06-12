package pageobjects.api.historical;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by dannyl on 2017-06-05.
 */
public class Q4Dataset {
    private ArrayList<String> currentPrices = new ArrayList<String>();
    private ArrayList<String> openPrices = new ArrayList<>();
    private ArrayList<String> yearHighPrices = new ArrayList<>();
    private ArrayList<String> yearLowPrices = new ArrayList<>();
    private ArrayList<String> dayLowPrices = new ArrayList<>();
    private ArrayList<String> dayHighPrices = new ArrayList<>();
    private ArrayList<String> prevClose = new ArrayList<>();
    private ArrayList<String> change = new ArrayList<>();
    private ArrayList<String> percentChange = new ArrayList<>();
    private String rawData;

    JSONParser parser = new JSONParser();
    // Fills the data-types initialized above with it's corresponding data
    public Q4Dataset (String input)
    {
        try{

            JSONObject dataSet = (JSONObject) parser.parse(input);
            // Create a JSONObject containing all the stock information

            currentPrices.add(dataSet.get("Ask").toString());
            openPrices.add(dataSet.get("Open").toString());
            yearHighPrices.add(dataSet.get("High52Weeks").toString());
            yearLowPrices.add(dataSet.get("Low52Weeks").toString());
            dayLowPrices.add(dataSet.get("Low").toString());
            dayHighPrices.add(dataSet.get("High").toString());
            change.add(dataSet.get("ChangeFromPreviousClose").toString());
            percentChange.add(dataSet.get("PercentChangeFromPreviousClose").toString());
            prevClose.add(dataSet.get("PreviousClose").toString());

            rawData = dataSet.toString();

        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public double getPrice()
    {
      return Double.parseDouble(currentPrices.get(0));
    }

    public double getChange()
    {
        return Double.parseDouble(change.get(0));
    }

    public double getChangeInPercent()
    {
        return Double.parseDouble(percentChange.get(0));
    }

    public double getDayHigh(){
        return Double.parseDouble(dayHighPrices.get(0));
    }

    public double getDayLow(){
        return Double.parseDouble(dayLowPrices.get(0));
    }

    public double getYearHigh(){
        return Double.parseDouble(yearHighPrices.get(0));
    }

    public double getYearLow(){
        return Double.parseDouble(yearLowPrices.get(0));
    }

    public double getOpen(){
        return Double.parseDouble(openPrices.get(0));
    }

    public double getPreviousClose(){
        return Double.parseDouble(prevClose.get(0));
    }
}
