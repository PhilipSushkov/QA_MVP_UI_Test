package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by philipsushkov on 2016-12-08.
 */

public class Functions {
    public static Properties propUI;

    public static Properties ConnectToPropUI(String sPathSharedUIMap) throws IOException {
        propUI = new Properties();
        propUI.load(new FileInputStream(sPathSharedUIMap));
        return propUI;
    }

}
