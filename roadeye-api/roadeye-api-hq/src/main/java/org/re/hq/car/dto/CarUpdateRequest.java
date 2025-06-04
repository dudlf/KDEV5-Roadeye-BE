package org.re.hq.car.dto;

import org.jspecify.annotations.Nullable;

public record CarUpdateRequest(
    @Nullable
    String name,
    @Nullable
    String imageUrl
) {
    public CarUpdateCommand toCommand() {
        return new CarUpdateCommand(
            name,
            imageUrl
        );
    }
}
