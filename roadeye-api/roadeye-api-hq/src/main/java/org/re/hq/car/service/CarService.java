package org.re.hq.car.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.hq.car.dto.CarCreationRequest;
import org.re.hq.car.dto.CarUpdateRequest;
import org.re.hq.car.dto.CarsStatusResult;
import org.re.hq.company.service.CompanyDomainService;
import org.re.hq.tenant.TenantId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    private final CompanyDomainService companyDomainService;
    private final CarDomainService carDomainService;

    public Page<Car> getCars(TenantId tenantId, Pageable pageable) {
        var company = companyDomainService.findById(tenantId.value());
        return carDomainService.getCars(company, pageable);
    }

    public List<Car> getAllCars(TenantId tenantId) {
        var company = companyDomainService.findById(tenantId.value());
        return carDomainService.getCars(company);
    }

    public CarsStatusResult getCarsStatus(TenantId tenantId) {
        var company = companyDomainService.findById(tenantId.value());
        return carDomainService.getCarsStatus(company);
    }

    public Car getCarById(TenantId tenantId, Long carId) {
        var company = companyDomainService.findById(tenantId.value());
        return carDomainService.getCarById(company, carId);
    }

    public Page<Car> searchByIgnitionStatus(TenantId tenantId, CarIgnitionStatus status, Pageable pageable) {
        var company = companyDomainService.findById(tenantId.value());
        return carDomainService.searchByIgnitionStatus(company, status, pageable);
    }

    public Long countByIgnitionStatus(TenantId tenantId, CarIgnitionStatus status) {
        var company = companyDomainService.findById(tenantId.value());
        return carDomainService.countByIgnitionStatus(company, status);
    }

    public Car createCar(TenantId tenantId, CarCreationRequest request) {
        var company = companyDomainService.findById(tenantId.value());
        var command = request.toCommand();
        return carDomainService.createCar(company, command);
    }

    public Car updateCarProfile(TenantId tenantId, Long carId, CarUpdateRequest request) {
        var company = companyDomainService.findById(tenantId.value());
        var car = carDomainService.getCarById(company, carId);
        var command = request.toCommand();
        return carDomainService.updateCarProfile(car, command);
    }

    public void deleteCar(TenantId tenantId, Long carId) {
        var company = companyDomainService.findById(tenantId.value());
        var car = carDomainService.getCarById(company, carId);
        carDomainService.deleteCar(car);
    }
}
