package org.re.hq.reservation.api;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.dto.CarResponse;
import org.re.hq.common.dto.PageResponse;
import org.re.hq.common.dto.SingleItemResponse;
import org.re.hq.reservation.dto.CarReservationCreateRequest;
import org.re.hq.reservation.dto.CarReservationResponse;
import org.re.hq.reservation.dto.DateTimeRange;
import org.re.hq.reservation.service.CarReservationService;
import org.re.hq.security.access.ManagerOnly;
import org.re.hq.security.userdetails.CompanyUserDetails;
import org.re.hq.tenant.TenantId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class CarReservationApi {
    private final CarReservationService carReservationService;


    @GetMapping
    public PageResponse<CarReservationResponse> findReservationByTenantId(TenantId tenantId, Pageable pageable) {
        Page<CarReservationResponse> reservations = carReservationService.findByTenantId(tenantId, pageable);
        return PageResponse.of(reservations);
    }

    @GetMapping("/{carId}")
    public PageResponse<CarReservationResponse> findReservationByCarId(@PathVariable Long carId, Pageable pageable) {
        Page<CarReservationResponse> reservations = carReservationService.findByCarId(carId, pageable);
        return PageResponse.of(reservations);
    }

    @GetMapping("/cars/available")
    public PageResponse<CarResponse> findAvailableCarReservations(
        @ModelAttribute DateTimeRange range,
        Pageable pageable
    ) {
        return PageResponse.of(carReservationService.findAvailableCarReservations(range, pageable), CarResponse::from);
    }

    @PostMapping
    public SingleItemResponse<CarReservationResponse> create(
        TenantId tenantId,
        CompanyUserDetails userDetails,
        @RequestBody CarReservationCreateRequest request
    ) {
        CarReservationResponse response = carReservationService.createCarReservation(tenantId, userDetails.getUserId(), request);
        return SingleItemResponse.of(response);
    }

    @ManagerOnly
    @PatchMapping("/{reservationId}/approve")
    public SingleItemResponse<CarReservationResponse> approveReservation(
        TenantId tenantId,
        CompanyUserDetails userDetails,
        @PathVariable Long reservationId
    ) {
        CarReservationResponse response = carReservationService.approveReservation(tenantId, reservationId, userDetails.getUserId());
        return SingleItemResponse.of(response);
    }

    @ManagerOnly
    @PatchMapping("/{reservationId}/reject")
    public SingleItemResponse<CarReservationResponse> rejectReservation(
        TenantId tenantId,
        CompanyUserDetails userDetails,
        @PathVariable Long reservationId,
        @RequestBody String reason
    ) {
        CarReservationResponse response = carReservationService.rejectReservation(tenantId, reservationId, userDetails.getUserId(), reason);
        return SingleItemResponse.of(response);
    }
}
