package org.re.hq.car.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.re.hq.car.domain.Car;
import org.re.hq.car.repository.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CarServiceTest {
    private CarRepository carRepository;
    private CarService carService;

    @BeforeEach
    void setUp() {
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
    }

    @Test
    void 차량_등록_성공() {
        // given
        Integer compId = 1001;
        String carName = "Sonata";
        String carImgUrl = "https://example.com/sonata.jpg";
        String carNumber = "12가1234";
        Integer carMileageSum = 0;

        Car car = Car.of(compId, carName, carImgUrl, carNumber, carMileageSum);

        when(carRepository.save(any(Car.class))).thenReturn(car);

        // when
        Car result = carService.createCar(compId, carName, carImgUrl, carNumber, carMileageSum);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getCarName()).isEqualTo(carName);
        assertThat(result.getCarNumber()).isEqualTo(carNumber);
        verify(carRepository, times(1)).save(any(Car.class));
    }
}
