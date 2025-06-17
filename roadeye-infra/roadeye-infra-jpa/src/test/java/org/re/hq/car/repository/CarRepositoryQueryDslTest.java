package org.re.hq.car.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.re.hq.car.CarFixture;
import org.re.hq.company.CompanyFixture;
import org.re.test.BaseQueryDslTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import({CarRepositoryQueryDslImpl.class})
public class CarRepositoryQueryDslTest extends BaseQueryDslTest {
    @Autowired
    CarRepositoryQueryDslImpl repository;

    @ParameterizedTest
    @DisplayName("차량 상태가 잘 조회되는지 확인")
    @CsvSource({
        "10, 5",
        " 0, 10",
        "10, 0",
        " 0, 0",
    })
    void getCarsStatus_withVariousConditions_returnsCorrectCounts(int nDrivingCars, int nIdleCars) {
        var company = em.merge(CompanyFixture.create());
        CarFixture.createDrivingList(nDrivingCars, company)
            .forEach(em::persist);
        CarFixture.createIdleList(nIdleCars, company)
            .forEach(em::persist);

        var status = repository.getCarsStatus(company);

        assertThat(status.drivings()).isEqualTo(nDrivingCars);
        assertThat(status.idles()).isEqualTo(nIdleCars);
    }
}
