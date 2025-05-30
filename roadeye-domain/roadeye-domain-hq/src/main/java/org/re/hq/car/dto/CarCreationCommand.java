package org.re.hq.car.dto;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarProfile;

public record CarCreationCommand(
    @NonNull
    String name,
    @NonNull
    String licenseNumber,
    @Nullable
    String imageUrl,
    @NonNull
    Integer mileageInitial
) {
    public Car toEntity(Long companyId) {
        var carProfile = new CarProfile(name, licenseNumber, imageUrl);
        return Car.of(companyId, carProfile, mileageInitial);
    }
}
