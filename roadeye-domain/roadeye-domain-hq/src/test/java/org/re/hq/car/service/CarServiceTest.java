package org.re.hq.car.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.hq.car.dto.CarCreationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import({CarService.class})
@DataJpaTest
class CarServiceTest {
    @Autowired
    CarService carService;

    @Test
    @DisplayName("차량 등록에 성공해야 한다.")
    void 차량등록_성공_테스트() {
        // given
        var companyId = 1L;
        var carName = "Test Car";
        var carLicenseNumber = "123가 4567";
        var carImageUrl = "http://example.com/car.jpg";
        var carMileageInitial = 10000;
        var command = new CarCreationCommand(
            carName,
            carLicenseNumber,
            carImageUrl,
            carMileageInitial
        );

        // when
        var car = carService.createCar(companyId, command);

        // then
        assertThat(car).isNotNull();
        assertThat(car.getCompanyId()).isEqualTo(companyId);
        assertThat(car.getProfile().getName()).isEqualTo(carName);
        assertThat(car.getProfile().getLicenseNumber()).isEqualTo(carLicenseNumber);
        assertThat(car.getProfile().getImageUrl()).isEqualTo(carImageUrl);
        assertThat(car.getMileage().getInitial()).isEqualTo(carMileageInitial);
    }
}
