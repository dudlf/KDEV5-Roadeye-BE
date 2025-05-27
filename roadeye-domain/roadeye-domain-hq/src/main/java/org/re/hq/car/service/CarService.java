package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.dto.CarCreationCommand;
import org.re.hq.car.dto.CarDisableCommand;
import org.re.hq.car.dto.CarUpdateCommand;
import org.re.hq.car.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Page<Car> getCars(Long companyId, Pageable pageable) {
        return carRepository.findByCompanyId(companyId, pageable);
    }

    public Car createCar(Long companyId, CarCreationCommand command) {
        var car = command.toEntity(companyId);
        return carRepository.save(car);
    }

    public Car updateCarProfile(Long companyId, Long carId, CarUpdateCommand command) {
        var car = getCar(companyId, carId);
        command.update(car);
        return carRepository.save(car);
    }

    public Car turnOnIgnition(Long companyId, Long carId, UUID transactionId) {
        var car = getCar(companyId, carId);
        car.turnOnIgnition(transactionId);
        return carRepository.save(car);
    }

    public Car turnOffIgnition(Long companyId, Long carId, UUID transactionId) {
        var car = getCar(companyId, carId);
        car.turnOffIgnition(transactionId);
        return carRepository.save(car);
    }

    public Car resetIgnitionStatus(Long companyId, Long carId) {
        var car = getCar(companyId, carId);
        car.resetIgnitionStatus();
        return carRepository.save(car);
    }

    public Car disable(Long companyId, Long carId, CarDisableCommand command) {
        var car = getCar(companyId, carId);
        car.disable(command.reason());
        return carRepository.save(car);
    }

    private Car getCar(Long companyId, Long carId) {
        return carRepository.findByCompanyIdAndId(companyId, carId)
            .orElseThrow(() -> new IllegalArgumentException("Car not found for companyId: " + companyId + " and carId: " + carId));
    }
}
