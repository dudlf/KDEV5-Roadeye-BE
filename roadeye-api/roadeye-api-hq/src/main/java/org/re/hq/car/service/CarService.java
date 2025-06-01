package org.re.hq.car.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.hq.company.service.CompanyService;
import org.re.hq.tenant.TenantId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    private final CompanyService companyService;
    private final CarDomainService carDomainService;

    public Page<Car> getCars(TenantId tenantId, Pageable pageable) {
        var company = companyService.findById(tenantId.value());
        return carDomainService.getCars(company, pageable);
    }

    public Car getCarById(TenantId tenantId, Long carId) {
        var company = companyService.findById(tenantId.value());
        return carDomainService.getCarById(company, carId);
    }

    public Page<Car> searchByIgnitionStatus(TenantId tenantId, CarIgnitionStatus status, Pageable pageable) {
        var company = companyService.findById(tenantId.value());
        return carDomainService.searchByIgnitionStatus(company, status, pageable);
    }

    public Long countByIgnitionStatus(TenantId tenantId, CarIgnitionStatus status) {
        var company = companyService.findById(tenantId.value());
        return carDomainService.countByIgnitionStatus(company, status);
    }
}
