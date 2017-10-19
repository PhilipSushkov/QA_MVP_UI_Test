package specs.api;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageobjects.api.GoldPriceCheck;
import util.SendEmailLinkedList;

import java.util.LinkedList;

public class GoldPriceCheckTest extends SendEmailLinkedList {
    @BeforeClass
    public static void setup() throws Exception {
        GoldPriceCheck.createFile();
    }

    @Test (priority = 1)
    public static void checkingSpotYahoo() throws Exception {
        Assert.assertTrue(GoldPriceCheck.checkAPIField("YAHOO", "SPOT", 0.01));
    }

    @Test (priority = 2)
    public static void checkingSpotTradingView() throws Exception {
        Assert.assertTrue(GoldPriceCheck.checkAPIField("TRADINGVIEW", "SPOT", 0.01));
    }

    @Test (priority = 3)
    public static void checkingOpenYahoo() throws Exception {
        Assert.assertTrue(GoldPriceCheck.checkAPIField("YAHOO", "OPEN", 0.01));;
    }

    @Test (priority = 4)
    public static void checkingOpenTradingView() throws Exception {
        Assert.assertTrue(GoldPriceCheck.checkAPIField("TRADINGVIEW", "OPEN", 0.01));
    }

    @Test (priority = 5)
    public static void checkingCloseYahoo() throws Exception {
        Assert.assertTrue(GoldPriceCheck.checkAPIField("YAHOO", "CLOSE", 0.01));
    }

    @Test (priority = 6)
    public static void checkingCloseTradingView() throws Exception {
        Assert.assertTrue(GoldPriceCheck.checkAPIField("TRADINGVIEW", "CLOSE", 0.01));
    }

    @AfterClass (alwaysRun = true)
    public static void teardown() throws Exception {
        LinkedList<String> results = new LinkedList<String>();
        results = GoldPriceCheck.compileResult();
        sendEmail(results);
    }
}
