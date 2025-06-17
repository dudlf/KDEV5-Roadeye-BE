package org.re.car.dto;

import org.jspecify.annotations.Nullable;

public record CarUpdateRequest(
    @Nullable
    String name,
    @Nullable
    String imageUrl
) {
    public org.re.car.dto.CarUpdateCommand toCommand() {
        return new org.re.car.dto.CarUpdateCommand(
            name,
            imageUrl
        );
    }
}
