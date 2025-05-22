package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarLocation {

    @Column(precision = 10, scale = 6)
    private BigDecimal coordLat;

    @Column(precision = 9, scale = 6)
    private BigDecimal coordLon;

    public CarLocation(BigDecimal coordLat, BigDecimal coordLon) {
        this.coordLat = coordLat;
        this.coordLon = coordLon;
    }
}
