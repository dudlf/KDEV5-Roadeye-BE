package org.re.car.dto;

public class CarCreationCommandFixture {
    public static org.re.car.dto.CarCreationCommand create() {
        var carName = "Test Car";
        var carLicenseNumber = "123가 4567";
        var carImageUrl = "http://example.com/car.jpg";
        var carMileageInitial = 10000;
        return new org.re.car.dto.CarCreationCommand(
            carName,
            carLicenseNumber,
            carImageUrl,
            carMileageInitial
        );
    }
}
