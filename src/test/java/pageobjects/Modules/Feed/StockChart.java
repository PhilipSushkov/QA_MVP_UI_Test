package pageobjects.Modules.Feed;

import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by zacharyk on 2017-06-21.
 */

public class StockChart extends AbstractPageObject {
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;

    public StockChart(WebDriver driver) {
        super(driver);
    }
}
