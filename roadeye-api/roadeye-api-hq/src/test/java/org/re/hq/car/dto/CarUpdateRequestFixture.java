package org.re.hq.car.dto;

public class CarUpdateRequestFixture {
    public static CarUpdateRequest create() {
        var name = "Updated Car Name";
        var imageUrl = "https://example.com/updated-car-image.jpg";
        return new CarUpdateRequest(
            name,
            imageUrl
        );
    }
}
