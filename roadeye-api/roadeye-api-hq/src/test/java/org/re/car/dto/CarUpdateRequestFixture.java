package org.re.car.dto;

public class CarUpdateRequestFixture {
    public static org.re.car.dto.CarUpdateRequest create() {
        var name = "Updated Car Name";
        var imageUrl = "https://example.com/updated-car-image.jpg";
        return new org.re.car.dto.CarUpdateRequest(
            name,
            imageUrl
        );
    }
}
