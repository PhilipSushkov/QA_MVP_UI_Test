package pageobjects.api;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

abstract class GetGoldValues {
    public static double getAPIValue(String URLValue, String type) throws Exception {
        double value = 0;
        URL urlValue = new URL(URLValue);
        InputStream in = urlValue.openStream();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(in);
            Element rootNode = document.getRootElement();
            List list = rootNode.getChildren("data");
            for (int ii = 0; ii < list.size(); ii++) {
                Element node = (Element) list.get(ii);
                /*System.out.println("Spot: " + node.getChildText("Quote"));
                System.out.println("Open: " + node.getChildText("Open"));
                System.out.println("Close: " + node.getChildText("Close"));*/
                if (type == "SPOT")
                    value = Double.parseDouble(node.getChildText("Quote").replaceAll(",", ""));
                else if (type == "OPEN")
                    value = Double.parseDouble(node.getChildText("Open").replaceAll(",", ""));
                else if (type == "CLOSE")
                    value = Double.parseDouble(node.getChildText("Close").replaceAll(",", ""));
                else
                    value = -1;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JDOMException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

    public static double getWebValues(String compareSite, String URL, String type, By spotPath, By openPath, By closePath) {
        ChromeDriver driver;
        driver = new ChromeDriver();
        driver.navigate().to(URL);
        System.out.println("Using " + compareSite + ", Testing " + type);
        double value;
        timeDelay(5000);
        try {
            switch (type) {
                case "SPOT":
                    value = Double.parseDouble(driver.findElement(spotPath).getText().replaceAll(",", ""));
                    break;
                case "OPEN":
                    value = Double.parseDouble(driver.findElement(openPath).getText().replaceAll(",", ""));
                    break;
                case "CLOSE":
                    value = Double.parseDouble(driver.findElement(closePath).getText().replaceAll(",", ""));
                    break;
                default:
                    System.out.println("ERROR! Invalid Type");
                    value = -1;
                    break;
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Problem with test case, must investigate -> FAIL");
            value = -1;
        }
        driver.quit();
        return value;
    }

    public static void timeDelay(int time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}