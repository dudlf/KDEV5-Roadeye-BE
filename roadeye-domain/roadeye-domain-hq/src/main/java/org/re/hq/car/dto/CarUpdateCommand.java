package org.re.hq.car.dto;

import org.re.hq.car.domain.Car;

public record CarUpdateCommand(
    String name,
    String imageUrl
) {
    public void update(Car car) {
        if (name != null) {
            car.getProfile().setName(name);
        }
        if (imageUrl != null) {
            car.getProfile().setImageUrl(imageUrl);
        }
    }
}
