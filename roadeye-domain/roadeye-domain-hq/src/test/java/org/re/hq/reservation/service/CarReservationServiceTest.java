package org.re.hq.reservation.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.re.hq.reservation.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CarReservationServiceTest {

    @Autowired
    private CarReservationService carReservationService;

    @Autowired
    private CarReservationRepository carReservationRepository;

    @Test
    void 예약을_승인합니다() {
        CarReservation reservation = CarReservation.createReservation(
            1L,
            10L,
            ReservationPeriod.of(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        );
        carReservationRepository.save(reservation);

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
        CarReservation reservation = CarReservation.createReservation(
            1L,
            10L,
            ReservationPeriod.of(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        );
        carReservationRepository.save(reservation);

        carReservationService.rejectReservation(reservation.getId(), 100L,"정비로 인하여 대여 불가", LocalDateTime.now());

        CarReservation updated = carReservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        assertAll(
                () -> assertThat(updated.getReserveStatus()).isEqualTo(ReserveStatus.REJECTED),
                () -> assertThat(updated.getApproverId()).isEqualTo(100L),
                () -> assertThat(updated.getProcessedAt()).isNotNull()
        );

    }
}
