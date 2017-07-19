package specs.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.api.DdosAttack;
import specs.ApiAbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by philipsushkov on 2017-07-17.
 */

public class CheckDdosAttack extends ApiAbstractSpec {

    private String sPathToFileDdos, sDataFileDdosJson;
    private JSONParser parser;
    private final int threadPoolSize = 1, iterations = 1;
    private FileWriter writer;

    @BeforeTest
    public void setUp() throws IOException {
        sPathToFileDdos = System.getProperty("user.dir") + propAPI.getProperty("dataPath_Ddos");
        sDataFileDdosJson = propAPI.getProperty("jsonData_Ddos");
        writer = new FileWriter("ddos_test_"+new Date()+".csv",false);

        writer.append("Request");
        writer.append(',');
        writer.append("Time");
        writer.append(',');
        writer.append("Referral");
        writer.append(',');
        writer.append("Response code");
        writer.append(',');
        writer.append("DateTime");
        writer.append('\n');
    }

    @DataProvider(parallel = true)
    public Object[][] DdosAtackData(Method method) {

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

    @Test(dataProvider = "DdosAtackData", threadPoolSize = threadPoolSize)
    public void CheckDdosAttack(JSONObject data) throws IOException {
        String sUrl = data.get("url").toString();

        for (int i=0; i<iterations; i++) {
            new DdosAttack().DdosAttackRequest(sUrl, data, writer);
        }

    }

    @AfterTest
    public void tearDown() throws IOException {
        writer.flush();
        writer.close();
    }

}
