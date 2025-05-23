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
        String name = "소나타";
        String number = "12가1234";
        String imgUrl = "https://example.com/car.jpg";
        int initial = 15000;

        CarProfile profile = new CarProfile(name, number, imgUrl);

        // when
        Car savedCar = carService.createCar(companyId, profile, initial);

        // then
        assertAll(
            () -> assertThat(savedCar.getCompanyId()).isEqualTo(companyId),
            () -> assertThat(savedCar.getProfile().getName()).isEqualTo(name),
            () -> assertThat(savedCar.getProfile().getNumber()).isEqualTo(number),
            () -> assertThat(savedCar.getProfile().getImageUrl()).isEqualTo(imgUrl),
            () -> assertThat(savedCar.getMileage().getInitial()).isEqualTo(initial)
        );
    }
}
