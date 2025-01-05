package tests;

import data_provider.DPCar;
import dto.CarDto;
import dto.UserDto;
import manager.ApplicationManager;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LetCarWorkPage;
import pages.LoginPage;
import pages.SearchPage;
import utils.Fuel;
import utils.RetryAnalyzer;
import utils.TestNGListener;

import java.lang.reflect.Method;
import java.util.Random;
import static utils.PropertiesReader.*;
import static utils.TakeScreenShot.*;

@Listeners(TestNGListener.class)

public class AddNewCarTests extends ApplicationManager {
    LoginPage loginPage;
    LetCarWorkPage letCarWorkPage;

    @BeforeMethod
    public void login() {
        UserDto user = new UserDto(getProperty("ligin.properties","email"),
                getProperty("ligin.properties","password"));

//        UserDtoLombok user = UserDtoLombok.builder()
//                .email("myphone@gmail.com")
//                .password("German1234!")
//                .build();
        new SearchPage(getDriver()).clickBtnLogin();
        loginPage = new LoginPage(getDriver());
        loginPage.typeLoginFormrFromProper(user);
        loginPage.clickBtnYalla();
        if (loginPage.isPopUpLoginMessagePresent("Logged in success")) {
            System.out.println("login success");
            loginPage.clickBtnOK();
            loginPage.clickBtnLetCarWork();
        } else
            System.out.println("Something went wrong");
    }

    @Test(dataProvider = "dataProviderCarFile", dataProviderClass = DPCar.class)
    public void addNewCarPositiveTest(CarDto car, Method method) {
        logger.info(method.getName()+ "start with data-->" + car.toString());

        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm(car);
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(letCarWorkPage
                .isPopUpMessagePresent(car.getManufacture() + " " + car.getModel() + " " + "added successful"));

    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void addNewCarPositiveTest() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("Mazda")
                .model("qwerty")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm(car);
        Assert.assertTrue(letCarWorkPage
                .isPopUpMessagePresent(car.getManufacture() + " " + car.getModel() + " " + "added successful"));

    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void addNewCarNegativeTestWrongMake() {
        CarDto car = CarDto.builder()
                .serialNumber(new Random().nextInt(1000) + "-055")
                .city("Haifa")
                .manufacture("")
                .model("qwerty")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm(car);
        takeScreenShot((TakesScreenshot) getDriver());
        Assert.assertTrue(letCarWorkPage.isElementPresentDOM("//*[text()=' Make is required ']",5));


    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void addNewCarNegativeTestWrong() {
        CarDto car = CarDto.builder()
                .serialNumber("466")
                .city("Haifa")
                .manufacture("Mazda")
                .model("6")
                .year("2022")
                .fuel(Fuel.HYBRID.getLocator())
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        letCarWorkPage = new LetCarWorkPage(getDriver());
        letCarWorkPage.typeLetCarWorkForm(car);
        Assert.assertTrue(letCarWorkPage.isElementPresentDOM("//*[text()='Car registration number is required']",5));


    }

}
