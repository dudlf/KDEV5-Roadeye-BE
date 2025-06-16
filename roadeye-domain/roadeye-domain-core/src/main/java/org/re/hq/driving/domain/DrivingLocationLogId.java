package org.re.hq.driving.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.reservation.domain.CarReservation;

import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingLocationLogId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resv_id", nullable = false, updatable = false)
    CarReservation carReservation;

    @Column(name = "seq", nullable = false, updatable = false)
    Long sequence;
}
