package specs.api;

import com.aventstack.extentreports.ExtentReports;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import specs.ApiAbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-07-17.
 */

public class CheckDdosAttack extends ApiAbstractSpec {

    private static String sPathToFileDdos, sDataFileDdosJson;
    private static JSONParser parser;
    private final int threadPoolSize = 15;

    private ExtentReports extent;

    @BeforeTest
    public void setUp() throws IOException {
        sPathToFileDdos = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Ddos");
        sDataFileDdosJson = propAPI.getProperty("jsonData_Ddos");
    }

    @DataProvider(parallel = true)
    public Object[][] NewsData(Method method) {

        parser = new JSONParser();
        System.out.println(sPathToFileDdos);
        System.out.println(sDataFileDdosJson);

        try {
            JSONArray stockDataArray = (JSONArray) parser.parse(new FileReader(sPathToFileDdos + sDataFileDdosJson));

            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < stockDataArray.size(); i++) {
                zoom.add(stockDataArray.get(i));
            }

            Object[][] stockData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                stockData[i][0] = zoom.get(i);
            }
            return stockData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
