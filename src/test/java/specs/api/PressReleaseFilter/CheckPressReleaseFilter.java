package specs.api.PressReleaseFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.api.PressReleaseFilter.FiltersAPI;
import specs.ApiAbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

public class CheckPressReleaseFilter {
    @Test
    public void testFilterApi() {
        try {
            String UUID = FiltersAPI.addFilter();
            System.out.println("UUID: " + UUID);
            //FiltersAPI.editFilter(UUID);
            //FiltersAPI.getAllFilters();
            //FiltersAPI.getOneFilter(UUID);
            FiltersAPI.deleteFilter(UUID);
            FiltersAPI.fieldCheck("TERMTYPE");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
