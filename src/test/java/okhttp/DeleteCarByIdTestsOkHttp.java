package okhttp;

import dto.CarDtoApi;
import dto.CarsDto;
import dto.TokenDto;
import dto.UserDtoLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.BaseApi.*;

public class DeleteCarByIdTestsOkHttp {

    TokenDto tokenDto;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login() {
        UserDtoLombok user = UserDtoLombok.builder()
                .username("myphone@gmail.com")
                .password("German1234!")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);
                System.out.println(tokenDto.getAccessToken());

            } else {
                System.out.println("login wrong");
            }
        } catch (IOException e) {
            System.out.println("login wrong , created exception");
        }
    }

    @Test
    public void deleteCarByIdTest() {
        String serialNumberFirstElement = "";
        CarDtoApi[] arrayCar = getAllUserCars();
        if (arrayCar != null) {
            serialNumberFirstElement = arrayCar[0].getSerialNumber();
            System.out.println("serial Number --> " + serialNumberFirstElement);
        } else
            Assert.fail("method get returned null");
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR + "/" + serialNumberFirstElement)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .delete()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("successful");
            } else
                Assert.fail("method delete is not succesful-->" + response.code());

        } catch (IOException e) {
            Assert.fail("created exception method delete");

        }
    }


    private CarDtoApi[] getAllUserCars() {
        Request request = new Request.Builder()
                .url(BASE_URL + GET_USER_CARS)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                CarsDto carsDto = GSON.fromJson(response.body().string(), CarsDto.class);
                return carsDto.getCars();
            } else {
                System.out.println("wrong get request -->" + response.code());
                return null;
            }
        } catch (IOException e) {
            System.out.println("Created exception get user cars, return null");
            e.printStackTrace();
            return null;
        }
    }
}
