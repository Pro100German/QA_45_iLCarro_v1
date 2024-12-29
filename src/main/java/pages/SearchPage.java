package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        setDriver(driver);
        driver.get("https://ilcarro.web.app/search");
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(xpath = "//a[text()=' Log in ']")
    WebElement btnLogin;
    @FindBy(xpath = "//a[text()=' Sign up ']")
    WebElement btnSignUp;

    @FindBy(id = "city")
    WebElement inputCity;
    @FindBy(id = "dates")
    WebElement inputDates;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnSubmit;

    @FindBy(xpath = "//button[@aria-label='Choose month and year']")
    WebElement btnMothYear;

    public void clickBtnSignUp() {
        btnSignUp.click();
    }

    public void clickBtnLogin() {
        btnLogin.click();
    }

    public void fillSearchCarFormWOCalendar(String city, String startDate, String endDate) {
        inputCity.click();
        inputCity.sendKeys(city);
        Actions actions = new Actions(driver);
        actions.moveToElement(inputCity, 0, 27).pause(2000).click().perform();
        //=======================================
        inputDates.click();
        inputDates.sendKeys(startDate + " - " + endDate);
        inputDates.sendKeys(Keys.ENTER);
    }

    public void fillSearchCarFormWithCalendar(String city, String startDate, String endDate) {
        inputCity.click();
        inputCity.sendKeys(city);
        Actions actions = new Actions(driver);
        actions.moveToElement(inputCity, 0, 27).pause(2000).click().perform();
        //=======================================
        inputDates.click();
        String[] startDayArray = startDate.split("/");
        String[] endDayArray = endDate.split("/");
        typeYearMonthDay(startDayArray[2],startDayArray[0],startDayArray[1]);
        typeYearMonthDay(endDayArray[2],endDayArray[0],endDayArray[1]);
        clickWait(btnSubmit,3);

    }

    private void typeYearMonthDay(String year, String month, String day){
        btnMothYear.click();
        driver.findElement(By.xpath(" //div[contains(text(),'" + year + "')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),'" + month.toUpperCase() + "')]")).click();
        driver.findElement(By.xpath("//div[contains(text(),'" + day + "')]")).click();
    }

}
