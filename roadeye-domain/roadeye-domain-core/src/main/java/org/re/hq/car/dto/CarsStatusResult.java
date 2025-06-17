package org.re.hq.car.dto;

public record CarsStatusResult(
    long drivings,
    long idles
) {
    public static CarsStatusResult EMPTY = new CarsStatusResult(0, 0);
}
