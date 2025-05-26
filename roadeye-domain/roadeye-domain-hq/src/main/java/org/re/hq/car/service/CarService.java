package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarProfile;
import org.re.hq.car.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /**
     * 차량 등록
     **/
    public Car createCar(Long companyId, CarProfile carProfile, int mileageInitial) {
        Car car = Car.of(companyId, carProfile, mileageInitial);
        return carRepository.save(car);
    }

}
