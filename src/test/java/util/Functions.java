package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by philipsushkov on 2016-12-08.
 */

public class Functions {
    private static Properties propUI;
    private static String currentDir;

    public static Properties ConnectToPropUI(String sPathSharedUIMap) throws IOException {
        propUI = new Properties();
        currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
        propUI.load(new FileInputStream(currentDir + sPathSharedUIMap));
        return propUI;
    }

}
