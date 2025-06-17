package org.re.car.controller;

import lombok.RequiredArgsConstructor;
import org.re.car.domain.CarIgnitionStatus;
import org.re.car.dto.CarCreationRequest;
import org.re.car.dto.CarDetailsResponse;
import org.re.car.dto.CarResponse;
import org.re.car.dto.CarUpdateRequest;
import org.re.car.service.CarService;
import org.re.hq.common.dto.ListResponse;
import org.re.hq.common.dto.PageResponse;
import org.re.hq.common.dto.SingleItemResponse;
import org.re.security.access.ManagerOnly;
import org.re.tenant.TenantId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarApi {
    private final CarService carService;

    @GetMapping
    public PageResponse<CarResponse> getCars(TenantId tenantId, Pageable pageable) {
        var page = carService.getCars(tenantId, pageable);
        return PageResponse.of(page, CarResponse::from);
    }

    @GetMapping("/all")
    public ListResponse<CarDetailsResponse> getAllCars(TenantId tenantId) {
        var page = carService.getAllCars(tenantId);
        return ListResponse.of(page, CarDetailsResponse::from);
    }

    @GetMapping("/{carId}")
    public SingleItemResponse<CarDetailsResponse> getCarById(TenantId tenantId, @PathVariable Long carId) {
        var car = carService.getCarById(tenantId, carId);
        return SingleItemResponse.of(car, CarDetailsResponse::from);
    }

    @GetMapping("/search/ignition")
    public PageResponse<CarResponse> getCarsByIgnition(TenantId tenantId, @RequestParam CarIgnitionStatus status, Pageable pageable) {
        var page = carService.searchByIgnitionStatus(tenantId, status, pageable);
        return PageResponse.of(page, CarResponse::from);
    }

    @GetMapping("/count/ignition")
    public SingleItemResponse<Long> countCarsByIgnition(TenantId tenantId, @RequestParam CarIgnitionStatus status) {
        var count = carService.countByIgnitionStatus(tenantId, status);
        return SingleItemResponse.of(count);
    }

    @ManagerOnly
    @PostMapping
    public SingleItemResponse<CarResponse> createCar(TenantId tenantId, @RequestBody CarCreationRequest request) {
        var createdCar = carService.createCar(tenantId, request);
        return SingleItemResponse.of(createdCar, CarResponse::from);
    }

    @ManagerOnly
    @PatchMapping("/{carId}")
    public SingleItemResponse<CarResponse> updateCarProfile(
        TenantId tenantId,
        @PathVariable Long carId,
        @RequestBody CarUpdateRequest request
    ) {
        var updatedCar = carService.updateCarProfile(tenantId, carId, request);
        return SingleItemResponse.of(updatedCar, CarResponse::from);
    }

    @ManagerOnly
    @DeleteMapping("/{carId}")
    public void deleteCar(
        TenantId tenantId,
        @PathVariable Long carId
    ) {
        carService.deleteCar(tenantId, carId);
    }
}
