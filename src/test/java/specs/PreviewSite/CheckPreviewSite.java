package specs.PreviewSite;

import org.junit.Before;
import org.junit.Test;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckPublicSite;

import static org.junit.Assert.fail;

/**
 * Created by jasons on 2016-11-30.
 */
public class CheckPreviewSite extends AbstractSpec {

    private CheckPublicSite publicTests = new CheckPublicSite();

    @Before
    public void goToPreviewSite() throws Exception {
        //new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
    }

    @Test
    public void versionNumberIsCorrect(){
        publicTests.versionNumberIsCorrect();
    }

    @Test
    public void stockChartXigniteWorks(){
        publicTests.stockChartXigniteWorks();
    }

    @Test
    public void stockQuoteWorks(){
        publicTests.stockQuoteWorks();
    }

    @Test
    public void stockQuoteValuesAreAccurate(){
        publicTests.stockQuoteValuesAreAccurate();
    }

    @Test
    public void stockChartTickertechWorks(){
        publicTests.stockChartTickertechWorks();
    }

    @Test
    public void historicalQuotesWork(){
        publicTests.historicalQuotesWork();
    }

    @Test
    public void historicalQuoteValuesAreAccurate(){
        publicTests.historicalQuoteValuesAreAccurate();
    }

    @Test
    public void financialReportsWork(){
        publicTests.financialReportsWork();
    }

    @Test
    public void pressReleasesWork(){
        publicTests.pressReleasesWork();
    }

    @Test
    public void eventsWork(){
        publicTests.eventsWork();
    }

    @Test
    public void presentationsWork(){
        publicTests.presentationsWork();
    }

    @Test
    public void secFilingsWork(){
        publicTests.secFilingsWork();
    }

    @Test
    public void peopleWork(){
        publicTests.peopleWork();
    }

    @Test
    public void rssPressReleaseWorks(){
        publicTests.rssPressReleaseWorks();
    }

    @Test
    public void rssEventsWorks(){
        publicTests.rssEventsWorks();
    }

    @Test
    public void rssPresentationsWorks(){
        publicTests.rssPresentationsWorks();
    }

    @Test
    public void emailAlertsWork(){
        publicTests.emailAlertsWork();
    }

    @Test
    public void unsubscribeEmailAlertsWorks(){
        publicTests.unsubscribeEmailAlertsWorks();
    }

    @Test
    public void investmentCalculatorWorks(){
        try {
            publicTests.investmentCalculatorWorks();
        }catch (AssertionError error){
            if (error.getMessage().equals("Invalid growth data is displayed.")){
                fail("Known Issue - WEB-10632 - Appearance of growth data is different on preview site.");
            }
            else {
                fail(error.getMessage());
            }
        }
    }

    @Test
    public void faqPageWorks(){
        publicTests.faqPageWorks();
    }
}
