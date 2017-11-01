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

    @BeforeTest
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

    @Test
    public static void editFilterTest() {
        try {
            Assert.assertTrue(filtersApi.editFilterCheck("NAME"));
            Assert.assertTrue(filtersApi.editFilterCheck("TERMTYPE"));
            Assert.assertTrue(filtersApi.editFilterCheck("TERM"));
            Assert.assertTrue(filtersApi.editFilterCheck("TYPE"));
            Assert.assertTrue(filtersApi.editFilterCheck("SORTORDER"));
            Assert.assertTrue(filtersApi.editFilterCheck("ID"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void deleteFilterTest() {
        try {
            Assert.assertTrue(filtersApi.deleteFilterCheck("INVALID"));
            Assert.assertTrue(filtersApi.deleteFilterCheck("SHORT"));
            Assert.assertTrue(filtersApi.deleteFilterCheck("LONG"));
            Assert.assertTrue(filtersApi.deleteFilterCheck("VALID"));
            Assert.assertTrue(filtersApi.deleteFilterCheck("REPEAT"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void getOneFilterTest() {
        try {
            Assert.assertTrue(filtersApi.getOneFilterCheck("INVALID"));
            Assert.assertTrue(filtersApi.getOneFilterCheck("SHORT"));
            Assert.assertTrue(filtersApi.getOneFilterCheck("LONG"));
            Assert.assertTrue(filtersApi.getOneFilterCheck("VALID"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void getAllFiltersTest() {
        try {
            Assert.assertTrue(filtersApi.getAllFiltersCheck());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
