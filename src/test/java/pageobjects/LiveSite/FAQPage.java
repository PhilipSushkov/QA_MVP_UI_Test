package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-30.
 */
public class FAQPage extends AbstractPageObject{

    private final By questionTop = By.className("FaqQuestionLink");
    private final By questionBelow = By.cssSelector(".FaqDetailList h3");
    private final By answer = By.className("FaqAnswer");
    private final By topOfFirstAnswer = By.cssSelector(".FaqAnswerLink");
    private final By firstBackToTop = By.cssSelector(".BackToTopLink");

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
    public int getFirstQuestionY(){
        waitForElement(topOfFirstAnswer);
        return findElement(topOfFirstAnswer).getLocation().getY();
    }

    public FAQPage clickFirstQuestion(){
        waitForElement(questionTop);
        findElement(questionTop).click();
        return this;
    }

    public FAQPage clickBackToTop(){
        waitForElement(firstBackToTop);
        findElement(firstBackToTop).click();
        return this;
    }
}
