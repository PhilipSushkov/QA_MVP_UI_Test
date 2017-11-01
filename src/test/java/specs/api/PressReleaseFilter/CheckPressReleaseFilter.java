package specs.api.PressReleaseFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
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
import java.util.logging.Filter;

public class CheckPressReleaseFilter {
    private static FiltersAPI filtersApi;

    public void setUp() throws Exception {
        filtersApi = new FiltersAPI();
    }

    @Test
    public void addFilterTest() {
        try {
            Assert.assertTrue(filtersApi.addFilterCheck("NAME"));
            Assert.assertTrue(filtersApi.addFilterCheck("TERMTYPE"));
            Assert.assertTrue(filtersApi.addFilterCheck("TERM"));
            Assert.assertTrue(filtersApi.addFilterCheck("TYPE"));
            Assert.assertTrue(filtersApi.addFilterCheck("SORTORDER"));
            Assert.assertTrue(filtersApi.addFilterCheck("ID"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /*@Test
    public void editFilterTest() {
        try {
            Assert.assertTrue(FiltersAPI.editFilterCheck("NAME"));
            Assert.assertTrue(FiltersAPI.editFilterCheck("TERMTYPE"));
            Assert.assertTrue(FiltersAPI.editFilterCheck("TERM"));
            Assert.assertTrue(FiltersAPI.editFilterCheck("TYPE"));
            Assert.assertTrue(FiltersAPI.editFilterCheck("SORTORDER"));
            Assert.assertTrue(FiltersAPI.editFilterCheck("ID"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteFilterTest() {
        try {
            Assert.assertTrue(FiltersAPI.deleteFilterCheck("INVALID"));
            Assert.assertTrue(FiltersAPI.deleteFilterCheck("SHORT"));
            Assert.assertTrue(FiltersAPI.deleteFilterCheck("LONG"));
            Assert.assertTrue(FiltersAPI.deleteFilterCheck("VALID"));
            Assert.assertTrue(FiltersAPI.deleteFilterCheck("REPEAT"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOneFilterTest() {
        try {
            Assert.assertTrue(FiltersAPI.getOneFilterCheck("INVALID"));
            Assert.assertTrue(FiltersAPI.getOneFilterCheck("SHORT"));
            Assert.assertTrue(FiltersAPI.getOneFilterCheck("LONG"));
            Assert.assertTrue(FiltersAPI.getOneFilterCheck("VALID"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getAllFiltersTest() {
        try {
            Assert.assertTrue(FiltersAPI.getAllFiltersCheck());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
