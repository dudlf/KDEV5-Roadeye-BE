package org.re.hq.car.controller;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.dto.CarResponse;
import org.re.hq.car.service.CarService;
import org.re.hq.common.dto.PageResponse;
import org.re.hq.tenant.TenantId;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
