package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.re.hq.car.domain.Car;
import org.re.hq.car.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CarServiceTest {

    @Autowired
    private CarService carService;


    @Test
    void 차량을_등록합니다() {
        // given
        Integer companyId = 1;
        String carName = "소나타";
        String carImgUrl = "https://example.com/car.jpg";
        String carNumber = "12가1234";
        Integer carMileageInit = 15000;

        // when
        Car savedCar = carService.createCar(companyId, carName, carImgUrl, carNumber, carMileageInit);

        // then
        assertThat(savedCar.getCompanyId()).isEqualTo(companyId);
        assertThat(savedCar.getCarName()).isEqualTo(carName);
        assertThat(savedCar.getCarNumber()).isEqualTo(carNumber);
        assertThat(savedCar.getCarImgUrl()).isEqualTo(carImgUrl);
        assertThat(savedCar.getMileage().getMileageInit()).isEqualTo(carMileageInit);
    }
}
