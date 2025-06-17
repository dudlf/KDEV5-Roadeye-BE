package org.re.car;

import org.re.car.domain.Car;
import org.re.car.domain.CarProfile;
import org.re.hq.company.CompanyFixture;
import org.re.hq.company.domain.Company;

import java.util.List;
import java.util.stream.IntStream;

public class CarFixture {
    public static Car create() {
        var company = CompanyFixture.create();
        var profile = new CarProfile("name", "licenseNumber", "imageUrl");
        var initialMileage = 0;
        return Car.of(company, profile, initialMileage);
    }

    public static Car create(Company company) {
        var profile = new CarProfile("name", "123가123", "imageUrl");
        var initialMileage = 0;
        return Car.of(company, profile, initialMileage);
    }

    public static List<Car> createList(int size) {
        return IntStream
            .range(0, size)
            .mapToObj((c) -> CarFixture.create())
            .toList();
    }
}
