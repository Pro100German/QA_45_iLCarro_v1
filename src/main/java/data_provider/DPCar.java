package data_provider;

import dto.CarDto;
import org.testng.annotations.DataProvider;
import utils.Fuel;


import static utils.RandomUtils.*;
import static utils.RandomUtils.generateString;

public class DPCar {
    @DataProvider
    public CarDto[] newAddCarDP(){
        CarDto car = CarDto.builder()
                .city(generateCity(1))
                .manufacture("Mazda")
                .model(generateNamber(1))
                .year(generateNamber(4))
                .fuel(Fuel.PETROL.getLocator())
                .seats(generateNamber(1))
                .carClass(generateString(1))
                .serialNumber(generateNamber(6))
                .pricePerDay(generateNamber(3))
                .about(generateString(10))
                .build();
        return new CarDto[]{car};
    }
}
