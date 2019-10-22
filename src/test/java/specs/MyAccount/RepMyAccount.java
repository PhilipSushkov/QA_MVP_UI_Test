package specs.MyAccount;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import specs.AbstractSpec;

/**
 * Created by PSUSHKOV on Oct, 2019
 **/
public class RepMyAccount {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static ExtentHtmlReporter htmlReporter;
    private static String filePath = System.getProperty("user.dir") + AbstractSpec.propUIMayAccount.getProperty("repPath_MyAccount") +
            "./MyAccountRep.html";

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

        htmlReporter.config().setDocumentTitle("Check My Account");
        htmlReporter.config().setReportName("Check My Account, positive and negative test cases");
        return htmlReporter;
    }

    public static ExtentTest createTest(String name, String description) {
        test = extent.createTest(name, description);
        return test;
    }
}
