package org.re.hq.car.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.car.repository.CarRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    /**
     * 차량 등록
     **/
    public Car createCar(Integer companyId, String carName, String carImgUrl, String carNumber, Integer carMileageInit) {
        Car car = Car.of(companyId, carName, carImgUrl, carNumber, carMileageInit);
        return carRepository.save(car);
    }

    /**
     * 회사별 차량 목록 조회 (10개씩 페이징)
     **/
    public Page<Car> getCarsByCompany(Integer companyId, int page) {
        Pageable pageable = PageRequest.of(page, 10); // 페이지당 10개
        return carRepository.findByCompanyId(companyId, pageable);
    }
}
