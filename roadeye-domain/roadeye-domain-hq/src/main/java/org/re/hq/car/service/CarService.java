package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.dto.CarCreationCommand;
import org.re.hq.car.dto.CarDisableCommand;
import org.re.hq.car.dto.CarUpdateCommand;
import org.re.hq.car.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car createCar(Long companyId, CarCreationCommand command) {
        var car = command.toEntity(companyId);
        return carRepository.save(car);
    }

    public Car updateCarProfile(Long companyId, Long carId, CarUpdateCommand command) {
        var car = carRepository.findByCompanyIdAndId(companyId, carId)
            .orElseThrow(() -> new IllegalArgumentException("Car not found for companyId: " + companyId + " and carId: " + carId));

        command.update(car);

        return carRepository.save(car);
    }

    public Car turnOnIgnition(Long companyId, Long carId, UUID transactionId) {
        var car = carRepository.findByCompanyIdAndId(companyId, carId)
            .orElseThrow(() -> new IllegalArgumentException("Car not found for companyId: " + companyId + " and carId: " + carId));

        car.turnOnIgnition(transactionId);
        return carRepository.save(car);
    }

    public Car turnOffIgnition(Long companyId, Long carId, UUID transactionId) {
        var car = carRepository.findByCompanyIdAndId(companyId, carId)
            .orElseThrow(() -> new IllegalArgumentException("Car not found for companyId: " + companyId + " and carId: " + carId));

        car.turnOffIgnition(transactionId);
        return carRepository.save(car);
    }

    public Car disable(Long companyId, Long carId, CarDisableCommand command) {
        var car = carRepository.findByCompanyIdAndId(companyId, carId)
            .orElseThrow(() -> new IllegalArgumentException("Car not found for companyId: " + companyId + " and carId: " + carId));

        car.disable(command.reason());
        return carRepository.save(car);
    }
}
