package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.dto.CarCreationCommand;
import org.re.hq.car.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car createCar(Long companyId, CarCreationCommand command) {
        var car = command.toEntity(companyId);
        return carRepository.save(car);
    }
}
