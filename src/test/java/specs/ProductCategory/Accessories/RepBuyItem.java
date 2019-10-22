package specs.ProductCategory.Accessories;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import specs.AbstractSpec;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/

public class RepBuyItem {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentHtmlReporter htmlReporter;
    private static String filePath = System.getProperty("user.dir") + AbstractSpec.propUIProdCategory.getProperty("repPath_BuyItem") +
            "./BuyItemRep.html";

    public static ExtentReports GetExtent() {
        if (extent != null)
            return extent; //avoid creating new instance of html file
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());
        return extent;
    }

    private static ExtentHtmlReporter getHtmlReporter() {

        htmlReporter = new ExtentHtmlReporter(filePath);

        // make the charts visible on report open
        htmlReporter.config().setChartVisibilityOnOpen(true);

        htmlReporter.config().setDocumentTitle("Check Buy Item");
        htmlReporter.config().setReportName("Check Buy Item, positive and negative test cases");
        return htmlReporter;
    }

    public static ExtentTest createTest(String name, String description) {
        test = extent.createTest(name, description);
        return test;
    }

}
