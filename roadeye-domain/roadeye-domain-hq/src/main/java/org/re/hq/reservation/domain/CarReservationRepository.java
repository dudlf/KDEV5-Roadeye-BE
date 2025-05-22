package org.re.hq.reservation.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
}
