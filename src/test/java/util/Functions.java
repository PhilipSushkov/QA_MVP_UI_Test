package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;


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

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}
