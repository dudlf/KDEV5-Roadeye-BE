package org.re.hq.car;

import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarProfile;
import org.re.hq.company.CompanyFixture;

import java.util.List;
import java.util.stream.IntStream;

public class CarFixture {
    public static Car create() {
        var company = CompanyFixture.create();
        var profile = new CarProfile("name", "licenseNumber", "imageUrl");
        var initialMileage = 0;
        return Car.of(company, profile, initialMileage);
    }

    public static List<Car> createList(int size) {
        return IntStream
            .range(0, size)
            .mapToObj((_) -> CarFixture.create())
            .toList();
    }
}
