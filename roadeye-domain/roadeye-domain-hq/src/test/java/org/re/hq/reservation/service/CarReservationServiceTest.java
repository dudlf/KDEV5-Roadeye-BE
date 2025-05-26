package org.re.hq.reservation.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.re.hq.reservation.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CarReservationServiceTest {

    @Autowired
    private CarReservationService carReservationService;

    @Autowired
    private CarReservationRepository carReservationRepository;

    CarReservation createCarReservation(Long carId, int start, int end) {
        CarReservation reservation = CarReservation.createReservation(
            carId,
            10L,
            ReservationPeriod.of(LocalDateTime.now().plusDays(start), LocalDateTime.now().plusDays(end)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        );
        carReservationRepository.save(reservation);

        return carReservationRepository.findById(reservation.getId()).get();
    }
    void rejectReservation(CarReservation reservation) {
        carReservationService.rejectReservation(reservation.getId(), 100L,"정비로 인하여 대여 불가", LocalDateTime.now());
    }

    @Test
    void 예약을_승인합니다() {
        CarReservation reservation = createCarReservation(1L,1,5);

        carReservationService.approveReservation(reservation.getId(), 100L, LocalDateTime.now());

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
        CarReservation reservation = createCarReservation(1L,1,5);

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
    void 동일한시간대에는_하나의_예약만_가능합니다(){
        LocalDateTime now = LocalDateTime.now();
        createCarReservation(1L,1,5);

        assertThatThrownBy(() -> carReservationService.createReservation(
            1L,
            10L,
            ReservationPeriod.of(now.plusDays(4), now.plusDays(7)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalStateException.class)
            .hasMessage("Reservation already exists.");
    }

    @Test
    void 반려된예약_시간대에는_예약이_가능합니다(){
        CarReservation reservation = createCarReservation(1L,1,5);
        rejectReservation(reservation);

        assertThatCode(() -> carReservationService.createReservation(
            reservation.getCarId(),
            10L,
            ReservationPeriod.of(reservation.getReservationPeriod().getRentStartAt(),
                reservation.getReservationPeriod().getRentEndAt()),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).doesNotThrowAnyException();
    }

    @Test
    void 특정시간대에_유효한예약이_존재하는_차량들(){
        createCarReservation(1L,1,5);
        createCarReservation(2L,1,5);
        createCarReservation(3L,3,5);
        createCarReservation(4L,10,12);

        List<Long> reservationIds = carReservationService.findCarIdsWithReservationPeriod(
            ReservationPeriod.of(LocalDateTime.now().plusDays(4),LocalDateTime.now().plusDays(8))
        );

        assertAll(
            () -> assertSame(1L, reservationIds.getFirst()),
            () -> assertSame(2L, reservationIds.get(1)),
            () -> assertSame(3L, reservationIds.get(2))
        );
    }

    @Test
    void 예약시간은_유효한시간이어야한다(){
        assertThatThrownBy(() -> carReservationService.createReservation(
            1L,
            10L,
            ReservationPeriod.of(LocalDateTime.now().minusHours(3), LocalDateTime.now().plusDays(1)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rent start time must be before renting end time");

        assertThatThrownBy(() -> carReservationService.createReservation(
            1L,
            10L,
            ReservationPeriod.of(LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(1)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        )).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rent end time must be after rent start time");

    }
}
