package tests;

import manager.ApplicationManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LetCarWorkPage;
import pages.ResultsPage;
import pages.SearchPage;

public class SearchCarTests extends ApplicationManager {
    SearchPage searchPage;

    @Test
    public void searchCarPositiveTest(){
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("Haifa", "12/25/2024", "12/27/2024");
        Assert.assertTrue(new ResultsPage(getDriver()).isUrlResultsPresent());
    }
    @Test
    public void searchCarNegativeTestWrongSearchData(){
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("Haifa", "11/25/2024", "12/27/2024");
        Assert.assertTrue(new LetCarWorkPage(getDriver()).isElementPresentDOM("//div[@class='ng-star-inserted']",3));
    }// You can't pick date before today
    @Test
    public void searchCarNegativeTestWrongCity(){
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWOCalendar("", "12/25/2024", "12/27/2024");
        Assert.assertTrue(searchPage.isElementPresentDOM("//*[text()=' City is required ']",3));
    }
    @Test
    public void searchCarPositiveTestWithCalendar(){
        logger.info("searchCarPositiveTestWithCalendar with data -->" + "Haifa" + "Jun/25/2025" + "Aug/27/2025");
        searchPage = new SearchPage(getDriver());
        searchPage.fillSearchCarFormWithCalendar("Haifa", "Jun/25/2025", "Aug/27/2025");
        Assert.assertTrue(new ResultsPage(getDriver()).isUrlResultsPresent());
    }
}
