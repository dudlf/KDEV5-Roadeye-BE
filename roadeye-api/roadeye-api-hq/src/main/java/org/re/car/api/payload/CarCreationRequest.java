package org.re.car.api.payload;

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
    public org.re.car.dto.CarCreationCommand toCommand() {
        return new org.re.car.dto.CarCreationCommand(
            name,
            licenseNumber,
            imageUrl,
            mileageInitial
        );
    }
}
