package pages;

import dto.CarDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LetCarWorkPage extends BasePage {
    public LetCarWorkPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    @FindBy(id = "pickUpPlace")
    WebElement inputLocation;
    @FindBy(xpath = "//div[@class='pac-item']")
    WebElement locationSubmit;
    @FindBy(id = "make")
    WebElement inputManufacture;

    public void typeLetCarWorkForm(CarDto car) {
        inputLocation.sendKeys(car.getCity());
        clickWait(locationSubmit, 5);
    }
}
