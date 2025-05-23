package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarProfile;
import org.re.hq.car.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CarServiceTest {

    @Autowired
    private CarService carService;


    @Test
    void 차량을_등록합니다() {
        // given
        Long companyId = 1L;
        String carName = "소나타";
        String carImgUrl = "https://example.com/car.jpg";
        String carNumber = "12가1234";
        int initial = 15000;

        CarProfile profile = new CarProfile(carName, carImgUrl, carNumber);

        // when
        Car savedCar = carService.createCar(companyId, profile, initial);

        // then
        assertAll(
            () -> assertThat(savedCar.getCompanyId()).isEqualTo(companyId),
            () -> assertThat(savedCar.getProfile().getName()).isEqualTo(carName),
            () -> assertThat(savedCar.getProfile().getNumber()).isEqualTo(carNumber),
            () -> assertThat(savedCar.getProfile().getImageUrl()).isEqualTo(carImgUrl),
            () -> assertThat(savedCar.getMileage().getInitial()).isEqualTo(initial)
        );
    }
}
