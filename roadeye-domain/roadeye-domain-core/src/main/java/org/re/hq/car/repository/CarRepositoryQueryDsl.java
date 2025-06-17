package org.re.hq.car.repository;

import org.re.hq.car.dto.CarsStatusResult;
import org.re.hq.company.domain.Company;

public interface CarRepositoryQueryDsl {
    CarsStatusResult getCarsStatus(Company company);
}
