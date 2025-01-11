package okhttp;

import dto.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.Fuel;

import java.io.IOException;
import java.util.Random;

import static utils.PropertiesReader.getProperty;

public class AddNewCarTestOkHttp implements BaseApi {
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
    public void addNewCarPositiveTest() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number" + i)
                .city("Haifa")
                .manufacture("Mazda")
                .model("6")
                .year("2022")
                .fuel("Dizel")
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .addHeader(AUTH, tokenDto.getAccessToken())
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            System.out.println(response.isSuccessful() + " code " + response.code());
            if(response.isSuccessful()){
                softAssert.assertEquals(response.code(),200);
                ResponseMassageDto responseMassageDto = GSON.fromJson(response.body().string(), ResponseMassageDto.class);
                softAssert.assertTrue(responseMassageDto.getMessage().equals("Car added successfully"));
                softAssert.assertAll();
            }else
                Assert.fail("response status code--> " + response.code());

        } catch (IOException e) {
            Assert.fail("created exception");

        }
    }
    @Test
    public void addNewCarNegativeTestWrongCityByApi() {
        int i = new Random().nextInt(10000);
        CarDtoApi carDtoApi = CarDtoApi.builder()
                .serialNumber("number" + i)
                .city("")
                .manufacture("Mazda")
                .model("6")
                .year("2022")
                .fuel("Dizel")
                .seats(4)
                .carClass("A")
                .pricePerDay(123.99)
                .about("About my car")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(carDtoApi), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CAR)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            System.out.println(response.isSuccessful() + " code " + response.code());
            Assert.assertEquals(response.code(),403);
        } catch (IOException e) {


        }
    }
    }

