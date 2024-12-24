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
}
