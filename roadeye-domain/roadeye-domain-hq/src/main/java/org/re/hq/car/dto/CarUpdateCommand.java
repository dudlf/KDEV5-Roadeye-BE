package org.re.hq.car.dto;

public record CarUpdateCommand(
    String name,
    String imageUrl
) {
}
