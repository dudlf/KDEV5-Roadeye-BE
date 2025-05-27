package org.re.hq.car.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.hq.car.dto.CarCreationCommandFixture;
import org.re.hq.car.dto.CarUpdateCommand;
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
        var command = CarCreationCommandFixture.create();

        // when
        var car = carService.createCar(companyId, command);

        // then
        assertThat(car).isNotNull();
        assertThat(car.getCompanyId()).isEqualTo(companyId);
        assertThat(car.getProfile().getName()).isEqualTo(command.name());
        assertThat(car.getProfile().getLicenseNumber()).isEqualTo(command.licenseNumber());
        assertThat(car.getProfile().getImageUrl()).isEqualTo(command.imageUrl());
        assertThat(car.getMileage().getInitial()).isEqualTo(command.mileageInitial());
    }

    @Test
    @DisplayName("차량 이름 변경이 가능해야 한다.")
    void 차량이름_변경_테스트() {
        // given
        var companyId = 1L;
        var creationCommand = CarCreationCommandFixture.create();
        var nextName = "Next Car Name";
        var updatedCommand = new CarUpdateCommand(nextName, null);

        // when
        var car = carService.createCar(companyId, creationCommand);
        var updatedCar = carService.updateCarProfile(companyId, car.getId(), updatedCommand);

        // then
        assertThat(updatedCar).isNotNull();
        assertThat(updatedCar.getProfile().getName()).isEqualTo(nextName);
        assertThat(updatedCar.getProfile().getLicenseNumber()).isEqualTo(creationCommand.licenseNumber());
    }

    @Test
    @DisplayName("차량 이미지 변경이 가능해야 한다.")
    void 차량이미지_변경_테스트() {
        // given
        var companyId = 1L;
        var creationCommand = CarCreationCommandFixture.create();
        var nextImageUrl = "http://example.com/next_car.jpg";
        var updatedCommand = new CarUpdateCommand(null, nextImageUrl);

        // when
        var car = carService.createCar(companyId, creationCommand);
        var updatedCar = carService.updateCarProfile(companyId, car.getId(), updatedCommand);

        // then
        assertThat(updatedCar).isNotNull();
        assertThat(updatedCar.getProfile().getImageUrl()).isEqualTo(nextImageUrl);
    }
}
