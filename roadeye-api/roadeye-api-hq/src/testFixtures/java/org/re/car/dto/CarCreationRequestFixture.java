package org.re.car.dto;

public class CarCreationRequestFixture {
    public static org.re.car.dto.CarCreationRequest create() {
        var name = "Test Car";
        var licensePlate = "TEST-1234";
        var imgUrl = "https://example.com/car.jpg";
        var mileageInitial = 0;
        return new org.re.car.dto.CarCreationRequest(
            name,
            licensePlate,
            imgUrl,
            mileageInitial
        );
    }
}
