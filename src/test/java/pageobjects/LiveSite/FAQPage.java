package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-30.
 */
public class FAQPage extends AbstractPageObject{

    private final By questionTop = By.className("FaqQuestionLink");
    private final By firstQuestion = By.xpath("//a[contains(@class,'FaqQuestionLink')][1]");
    private final By questionBelow = By.cssSelector(".FaqDetailList h3");
    private final By answer = By.className("FaqAnswer");
    private final By topOfFirstAnswer = By.cssSelector(".FaqAnswerLink");
    private final By firstBackToTop = By.xpath("//a[contains(@class,'BackToTopLink')][1]");

    public FAQPage(WebDriver driver) {
        super(driver);
    }

    // returns number of questions displayed at the top of the page
    public int getNumQuestionsTop(){
        return findElements(questionTop).size();
    }

    // returns number of questions displayed below (alongside the answers)
    public int getNumQuestionsBelow(){
        return findElements(questionBelow).size();
    }

    public int getNumAnswers(){
        return findElements(answer).size();
    }

    // returns the vertical position (from the top of the page in pixels) of the first question below
    public long getFirstQuestionY(){
        waitForElement(topOfFirstAnswer);
        long value = findElement(topOfFirstAnswer).getLocation().getY();
        return value;
    }

    public boolean doesAnchorLinkWork(){
        //checks to see if 1) there is an anchor link with the same text as the question
        //and 2) that that link has a url with the same 'name' as the question
        waitForElement(firstQuestion);
        String link = findElement(firstQuestion).getAttribute("href");
        String[] linkArray = link.split("#");
        String questionNum = linkArray[1];

        By questionTitle = By.xpath("//a[contains(@name," + " '" + questionNum+ "'" + ")]");

        //Question exists
        try{
            waitForElement(questionTitle);
            findElement(questionTitle);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public FAQPage clickFirstQuestion(){
        waitForElement(firstQuestion);
        findElement(firstQuestion).click();
        return this;
    }

    public FAQPage clickBackToTop(){
        waitForElement(firstBackToTop);
        findElement(firstBackToTop).click();
        return this;
    }

}
