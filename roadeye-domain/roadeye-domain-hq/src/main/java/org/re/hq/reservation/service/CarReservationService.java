package org.re.hq.reservation.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.reservation.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CarReservationService {
    private final CarReservationRepository carReservationRepository;

    /**
     * 예약 승인 처리
     */
    public void approveReservation(Long reservationId, Long approverId, LocalDateTime processedAt) {
        CarReservation reservation = carReservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID"));

        reservation.approve(approverId, processedAt);
    }

    /**
     * 예약 반려 처리
     */
    public void rejectReservation(Long reservationId, Long approverId, String rejectReason, LocalDateTime processedAt) {
        CarReservation reservation = carReservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found with ID"));

        reservation.reject(approverId, rejectReason, processedAt);
    }
}
