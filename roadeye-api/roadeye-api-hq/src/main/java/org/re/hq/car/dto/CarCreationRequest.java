package org.re.hq.car.dto;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public record CarCreationRequest(
    @NonNull
    String name,
    @NonNull
    String licenseNumber,
    @Nullable
    String imageUrl,
    @NonNull
    Integer mileageInitial
) {
    public CarCreationCommand toCommand() {
        return new CarCreationCommand(
            name,
            licenseNumber,
            imageUrl,
            mileageInitial
        );
    }
}
