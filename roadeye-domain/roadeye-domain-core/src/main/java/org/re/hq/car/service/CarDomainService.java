package org.re.hq.car.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.hq.car.dto.CarCreationCommand;
import org.re.hq.car.dto.CarDisableCommand;
import org.re.hq.car.dto.CarUpdateCommand;
import org.re.hq.car.exception.CarDomainException;
import org.re.hq.car.repository.CarRepository;
import org.re.hq.company.domain.Company;
import org.re.hq.domain.common.DomainService;
import org.re.hq.domain.common.EntityLifecycleStatus;
import org.re.hq.domain.exception.DomainException;
import org.re.mdtlog.domain.TransactionUUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class CarDomainService {
    private final CarRepository carRepository;

    public Page<Car> getCars(Company company, Pageable pageable) {
        var companyId = company.getId();
        return carRepository.findByCompanyIdAndStatus(companyId, EntityLifecycleStatus.ACTIVE, pageable);
    }

    public List<Car> getCars(Company company) {
        var companyId = company.getId();
        return carRepository.findAllByCompanyIdAndStatus(companyId, EntityLifecycleStatus.ACTIVE);
    }

    public Page<Car> getCarsByStatus(Company company, EntityLifecycleStatus status, Pageable pageable) {
        var companyId = company.getId();
        return carRepository.findByCompanyIdAndStatus(companyId, status, pageable);
    }

    public Car getCarById(Long carId) {
        return carRepository.findByIdAndStatus(carId, EntityLifecycleStatus.ACTIVE)
            .orElseThrow(() -> new DomainException(CarDomainException.CAR_NOT_FOUND));
    }

    public Car getCarById(Company company, Long carId) {
        var companyId = company.getId();
        return carRepository.findByCompanyIdAndIdAndStatus(companyId, carId, EntityLifecycleStatus.ACTIVE)
            .orElseThrow(() -> new DomainException(CarDomainException.CAR_NOT_FOUND));
    }

    public Page<Car> searchByIgnitionStatus(Company company, CarIgnitionStatus status, Pageable pageable) {
        var companyId = company.getId();
        return carRepository.findByCompanyIdAndIgnitionStatusAndStatus(companyId, status, EntityLifecycleStatus.ACTIVE, pageable);
    }

    public Long countCarsByStatus(Company company, EntityLifecycleStatus status) {
        var companyId = company.getId();
        return carRepository.countByCompanyIdAndStatus(companyId, status);
    }

    public Long countByIgnitionStatus(Company company, CarIgnitionStatus status) {
        var companyId = company.getId();
        return carRepository.countByCompanyIdAndIgnitionStatusAndStatus(companyId, status, EntityLifecycleStatus.ACTIVE);
    }

    public Car createCar(Company company, CarCreationCommand command) {
        var car = command.toEntity(company);
        return carRepository.save(car);
    }

    public Car updateCarProfile(Car car, CarUpdateCommand command) {
        car.update(command);
        return car;
    }

    public Car turnOnIgnition(Car car, TransactionUUID transactionId) {
        car.turnOnIgnition(transactionId);
        return car;
    }

    public Car turnOffIgnition(Car car, TransactionUUID transactionId) {
        car.turnOffIgnition(transactionId);
        return car;
    }

    public Car resetIgnitionStatus(Car car) {
        car.resetIgnitionStatus();
        return car;
    }

    public void deleteCar(Car car) {
        car.delete();
    }

    public Car disable(Car car, CarDisableCommand command) {
        car.disable(command.reason());
        return car;
    }

}
