package org.re.hq.reservation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.service.CarDomainService;
import org.re.hq.company.domain.Company;
import org.re.hq.company.service.CompanyService;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.service.EmployeeDomainService;
import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.dto.CarReservationCreateRequest;
import org.re.hq.reservation.dto.CarReservationResponse;
import org.re.hq.reservation.dto.CreateCarReservationCommand;
import org.re.hq.reservation.dto.DateTimeRange;
import org.re.hq.tenant.TenantId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationService {
    private final CarReservationDomainService carReservationDomainService;
    private final CarDomainService carDomainService;
    private final EmployeeDomainService employeeDomainService;
    private final CompanyService companyService;

    public Page<CarReservationResponse> findByTenantId(TenantId tenantId, Pageable pageable) {
        Page<CarReservation> reservations = carReservationDomainService.findReservationWithCompanyId(tenantId.value(), pageable);
        return reservations.map(CarReservationResponse::from);
    }

    public Page<CarReservationResponse> findByCarId(Long carId, Pageable pageable) {
        Page<CarReservation> reservations = carReservationDomainService.findReservationWithCarId(carId, pageable);
        return reservations.map(CarReservationResponse::from);
    }

    public CarReservationResponse approveReservation(TenantId tenantId, Long reservationId, Long approverId) {
        LocalDateTime now = LocalDateTime.now();
        Employee approver = employeeDomainService.read(tenantId.value(), approverId);
        CarReservation reservation = carReservationDomainService.approveReservation(reservationId, approver, now);
        return CarReservationResponse.from(reservation);
    }

    public CarReservationResponse rejectReservation(TenantId tenantId, Long reservationId, Long approverId, String rejectReason) {
        LocalDateTime now = LocalDateTime.now();
        Employee approver = employeeDomainService.read(tenantId.value(), approverId);
        CarReservation reservation = carReservationDomainService.rejectReservation(reservationId, approver, rejectReason, now);
        return CarReservationResponse.from(reservation);
    }

    public CarReservationResponse createCarReservation(TenantId tenantId, Long reserverId, CarReservationCreateRequest request) {
        Company company = companyService.findById(tenantId.value());
        Car car = carDomainService.getCarById(company, request.carId());
        Employee reserver = employeeDomainService.read(tenantId.value(), reserverId);
        CreateCarReservationCommand command = request.toCommand(tenantId.value(), car, reserver);
        CarReservation reservation = carReservationDomainService.createReservation(command);
        return CarReservationResponse.from(reservation);
    }

    public Page<Car> findAvailableCarReservations(DateTimeRange range, Pageable pageable) {
        return carDomainService.findAvailableCarReservations(range, pageable);
    }
}
