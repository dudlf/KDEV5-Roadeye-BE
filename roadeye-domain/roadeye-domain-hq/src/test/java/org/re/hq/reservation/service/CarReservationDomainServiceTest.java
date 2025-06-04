package org.re.hq.reservation.service;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.re.hq.car.domain.Car;
import org.re.hq.car.dto.CarCreationCommandFixture;
import org.re.hq.car.service.CarDomainService;
import org.re.hq.company.domain.Company;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.service.EmployeeDomainService;
import org.re.hq.reservation.CarReservationFixture;
import org.re.hq.reservation.domain.*;
import org.re.hq.test.api.extension.CarParameterResolver;
import org.re.hq.test.api.extension.EmployeeParameterResolver;
import org.re.hq.test.supports.WithCar;
import org.re.hq.test.supports.WithCompany;
import org.re.hq.test.supports.WithEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertAll;


@Import({CarDomainService.class, CarReservationDomainService.class, EmployeeDomainService.class})
@DataJpaTest
@WithCompany
@WithEmployee
@WithCar
class CarReservationDomainServiceTest {

    @Autowired
    private CarReservationDomainService carReservationDomainService;

    @Autowired
    private CarReservationRepository carReservationRepository;

    @Autowired
    private CarDomainService carDomainService;

    void rejectReservation(CarReservation reservation, Employee rootEmployee) {
        carReservationDomainService.rejectReservation(reservation.getId(), rootEmployee,"정비로 인하여 대여 불가", LocalDateTime.now());
    }

    @Test
    void 예약을_승인합니다(Company company, Car car, Employee normalEmployee, Employee rootEmployee) {
        var carReservation = CarReservationFixture.create(car, normalEmployee, 1,5);
        carReservationRepository.save(carReservation);

        CarReservation reservation = carReservationRepository.findAll().getFirst();
        carReservationDomainService.approveReservation(reservation.getId(), rootEmployee, LocalDateTime.now());

        CarReservation updated = carReservationRepository.findById(reservation.getId())
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        assertAll(
                () -> assertThat(updated.getReserveStatus()).isEqualTo(ReserveStatus.APPROVED),
                () -> assertThat(updated.getApprover().getId()).isEqualTo(rootEmployee.getId()),
                () -> assertThat(updated.getProcessedAt()).isNotNull()
        );

    }

    @Test
    void 예약을_반려합니다(Company company, Car car, Employee normalEmployee, Employee rootEmployee) {
        var carReservation = CarReservationFixture.create(car, normalEmployee, 1,5);
        carReservationRepository.save(carReservation);

        CarReservation reservation = carReservationRepository.findAll().getFirst();

        rejectReservation(reservation, rootEmployee);

        CarReservation updated = carReservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        assertAll(
                () -> assertThat(updated.getReserveStatus()).isEqualTo(ReserveStatus.REJECTED),
                () -> assertThat(updated.getApprover()).isEqualTo(rootEmployee),
                () -> assertThat(updated.getProcessedAt()).isNotNull()
        );

    }

    @Test
    void 동일한시간대에는_하나의_예약만_가능합니다(Company company, Car car, Employee normalEmployee) {
        var carReservation = CarReservationFixture.create(car, normalEmployee, 1,5);
        carReservationRepository.save(carReservation);

        CarReservation reservation = carReservationRepository.findAll().getFirst();
        LocalDateTime now = LocalDateTime.now();

        assertThatThrownBy(() -> carReservationDomainService.createReservation(
            company.getId(),
            reservation.getCar(),
            normalEmployee,
            ReservationPeriod.of(now.plusDays(4), now.plusDays(7)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalStateException.class)
            .hasMessage("Reservation already exists.");
    }

    @Test
    void 반려된예약_시간대에는_예약이_가능합니다(Company company, Car car, Employee normalEmployee, Employee rootEmployee) {
        var carReservation = CarReservationFixture.create(car, normalEmployee, 1,5);
        carReservationRepository.save(carReservation);

        CarReservation reservation = carReservationRepository.findAll().getFirst();

        rejectReservation(reservation, rootEmployee);

        assertThatCode(() -> carReservationDomainService.createReservation(
            reservation.getCompanyId(),
            reservation.getCar(),
            normalEmployee,
            ReservationPeriod.of(reservation.getReservationPeriod().getRentStartAt(),
                reservation.getReservationPeriod().getRentEndAt()),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).doesNotThrowAnyException();
    }

    @Test
    void 특정시간대에_유효한예약이_존재하는_차량들(Company company, Employee normalEmployee) {
        for (int i = 0; i < 3; i++) {
            var command = CarCreationCommandFixture.create();
            carDomainService.createCar(company, command);
        }
        Page<Car> cars = carDomainService.getCars(company, Pageable.ofSize(10));
        List<CarReservation> carReservations = List.of(
            CarReservationFixture.create(cars.getContent().get(0),normalEmployee, 1,5),
            CarReservationFixture.create(cars.getContent().get(1),normalEmployee,3,5),
            CarReservationFixture.create(cars.getContent().get(2),normalEmployee,10,12)
        );
        carReservationRepository.saveAll(carReservations);

        List<Long> reservationIds = carReservationDomainService.findCarIdsWithReservationPeriod(
            company.getId(),
            ReservationPeriod.of(LocalDateTime.now().plusDays(4),LocalDateTime.now().plusDays(8))
        );

        assertAll(
            () -> assertThat(reservationIds).asInstanceOf(InstanceOfAssertFactories.LIST).hasSize(2),
            () -> assertThat(reservationIds).asInstanceOf(InstanceOfAssertFactories.LIST).contains(cars.getContent().get(0).getId(),cars.getContent().get(1).getId())
        );
    }

    @Test
    void 예약시간은_유효한시간이어야한다(Company company, Car car, Employee normalEmployee, Employee rootEmployee) {
        assertThatThrownBy(() -> carReservationDomainService.createReservation(
            1L,
            car,
            normalEmployee,
            ReservationPeriod.of(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusDays(1)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rent start time must be after current time");

        assertThatThrownBy(() -> carReservationDomainService.createReservation(
            1L,
            car,
            normalEmployee,
            ReservationPeriod.of(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(1)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rent end time must be after rent start time");
    }

    @Test
    void 예약목록_조회(Company company, Employee normalEmployee, Employee rootEmployee) {
        for (int i = 0; i < 3; i++) {
            var command = CarCreationCommandFixture.create();
            carDomainService.createCar(company, command);
        }
        Page<Car> cars = carDomainService.getCars(company, Pageable.ofSize(10));
        List<CarReservation> carReservations = List.of(
            CarReservationFixture.create(cars.getContent().get(0),normalEmployee,1,5),
            CarReservationFixture.create(cars.getContent().get(1),normalEmployee,3,5),
            CarReservationFixture.create(cars.getContent().get(2),normalEmployee,10,12)
        );
        carReservationRepository.saveAll(carReservations);

        assertThat(carReservationDomainService.findReservationWithCompanyId(company.getId(), Pageable.ofSize(10)).getTotalElements()).isEqualTo(3);
    }

    @Test
    void 차량별_예약목록_조회(Company company, Employee normalEmployee, Employee rootEmployee) {
        for (int i = 0; i < 3; i++) {
            var command = CarCreationCommandFixture.create();
            carDomainService.createCar(company, command);
        }
        Page<Car> cars = carDomainService.getCars(company, Pageable.ofSize(10));
        List<CarReservation> carReservations = List.of(
            CarReservationFixture.create(cars.getContent().get(0),normalEmployee,1,5),
            CarReservationFixture.create(cars.getContent().get(0),normalEmployee,6,8),
            CarReservationFixture.create(cars.getContent().get(1),normalEmployee,3,5),
            CarReservationFixture.create(cars.getContent().get(2),normalEmployee,10,12)
        );
        carReservationRepository.saveAll(carReservations);

        assertThat(carReservationDomainService.findReservationWithCarId(cars.getContent().get(0).getId(),Pageable.ofSize(10)).getTotalElements()).isEqualTo(2);
    }
}
