package org.re.hq.reservation.service;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.re.hq.car.domain.Car;
import org.re.hq.car.dto.CarCreationCommandFixture;
import org.re.hq.car.service.CarService;
import org.re.hq.company.domain.Company;
import org.re.hq.reservation.CarReservationFixture;
import org.re.hq.reservation.domain.*;
import org.re.hq.test.supports.WithCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertAll;


@Import({CarService.class, CarReservationDomainService.class})
@DataJpaTest
@WithCompany
class CarReservationDomainServiceTest {

    @Autowired
    private CarReservationDomainService carReservationDomainService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarReservationRepository carReservationRepository;

    @BeforeEach
    void setUp(Company company) {
        var command = CarCreationCommandFixture.create();
        carService.createCar(company, command);
        Long carId = carService.getCars(company, Pageable.ofSize(10)).getContent().get(0).getId();
        CarReservation reservation = CarReservationFixture.create(company.getId(),carId,1,5);
        carReservationRepository.save(reservation);
    }

    void rejectReservation(CarReservation reservation) {
        carReservationDomainService.rejectReservation(reservation.getId(), 100L,"정비로 인하여 대여 불가", LocalDateTime.now());
    }

    @Test
    void 예약을_승인합니다() {
        CarReservation reservation = carReservationRepository.findAll().getFirst();
        carReservationDomainService.approveReservation(reservation.getId(), 100L, LocalDateTime.now());

        CarReservation updated = carReservationRepository.findById(reservation.getId())
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        assertAll(
                () -> assertThat(updated.getReserveStatus()).isEqualTo(ReserveStatus.APPROVED),
                () -> assertThat(updated.getApproverId()).isEqualTo(100L),
                () -> assertThat(updated.getProcessedAt()).isNotNull()
        );

    }

    @Test
    void 예약을_반려합니다() {
        CarReservation reservation = carReservationRepository.findAll().getFirst();

        rejectReservation(reservation);

        CarReservation updated = carReservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        assertAll(
                () -> assertThat(updated.getReserveStatus()).isEqualTo(ReserveStatus.REJECTED),
                () -> assertThat(updated.getApproverId()).isEqualTo(100L),
                () -> assertThat(updated.getProcessedAt()).isNotNull()
        );

    }

    @Test
    void 동일한시간대에는_하나의_예약만_가능합니다(Company company) {
        LocalDateTime now = LocalDateTime.now();
        CarReservation reservation = carReservationRepository.findAll().getFirst();
        assertThatThrownBy(() -> carReservationDomainService.createReservation(
            company.getId(),
            reservation.getCarId(),
            10L,
            ReservationPeriod.of(now.plusDays(4), now.plusDays(7)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalStateException.class)
            .hasMessage("Reservation already exists.");
    }

    @Test
    void 반려된예약_시간대에는_예약이_가능합니다(){
        CarReservation reservation = carReservationRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        rejectReservation(reservation);

        assertThatCode(() -> carReservationDomainService.createReservation(
            reservation.getCompanyId(),
            reservation.getCarId(),
            10L,
            ReservationPeriod.of(reservation.getReservationPeriod().getRentStartAt(),
                reservation.getReservationPeriod().getRentEndAt()),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).doesNotThrowAnyException();
    }

    @Test
    void 특정시간대에_유효한예약이_존재하는_차량들(Company company){
        for (int i = 0; i < 3; i++) {
            var command = CarCreationCommandFixture.create();
            carService.createCar(company, command);
        }
        Page<Car> cars = carService.getCars(company, Pageable.ofSize(10));
        List<CarReservation> carReservations = List.of(
            CarReservationFixture.create(company.getId(), cars.getContent().get(0).getId(),1,5),
            CarReservationFixture.create(company.getId(), cars.getContent().get(1).getId(),3,5),
            CarReservationFixture.create(company.getId(), cars.getContent().get(2).getId(),10,12)
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
    void 예약시간은_유효한시간이어야한다(){
        assertThatThrownBy(() -> carReservationDomainService.createReservation(
            1L,
            10L,
            100L,
            ReservationPeriod.of(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusDays(1)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rent start time must be after current time");

        assertThatThrownBy(() -> carReservationDomainService.createReservation(
            1L,
            10L,
               100L,
            ReservationPeriod.of(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(1)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rent end time must be after rent start time");
    }

    @Test
    void 예약목록_조회(Company company){
        for (int i = 0; i < 3; i++) {
            var command = CarCreationCommandFixture.create();
            carService.createCar(company, command);
        }
        Page<Car> cars = carService.getCars(company, Pageable.ofSize(10));
        List<CarReservation> carReservations = List.of(
            CarReservationFixture.create(company.getId(), cars.getContent().get(0).getId(),1,5),
            CarReservationFixture.create(company.getId(), cars.getContent().get(1).getId(),3,5),
            CarReservationFixture.create(company.getId(), cars.getContent().get(2).getId(),10,12)
        );
        carReservationRepository.saveAll(carReservations);

        assertThat(carReservationDomainService.findReservationWithCompanyId(company.getId(), Pageable.ofSize(10)).getTotalElements()).isEqualTo(3);
    }

    @Test
    void 차량별_예약목록_조회(Company company){
        for (int i = 0; i < 3; i++) {
            var command = CarCreationCommandFixture.create();
            carService.createCar(company, command);
        }
        List<Car> cars = carService.getCars(company, Pageable.ofSize(10)).getContent();
        List<CarReservation> carReservations = List.of(
            CarReservationFixture.create(company.getId(), cars.get(0).getId(),1,5),
            CarReservationFixture.create(company.getId(), cars.get(0).getId(),6,8),
            CarReservationFixture.create(company.getId(), cars.get(1).getId(),3,5),
            CarReservationFixture.create(company.getId(), cars.get(2).getId(),10,12)
        );
        carReservationRepository.saveAll(carReservations);

        assertThat(carReservationDomainService.findReservationWithCarId(cars.get(0).getId(),Pageable.ofSize(10)).getTotalElements()).isEqualTo(2);
    }
}
