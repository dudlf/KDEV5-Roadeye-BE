package org.re.hq.car.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.QCar;
import org.re.hq.car.dto.CarsStatusResult;
import org.re.hq.company.domain.Company;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CarRepositoryQueryDslImpl implements CarRepositoryQueryDsl {
    private final JPAQueryFactory queryFactory;

    @Override
    public CarsStatusResult getCarsStatus(Company company) {
        var car = QCar.car;

        var nDriving = queryFactory.select(car.count())
            .from(car)
            .where(car.company.eq(company)
                .and(car.mdtStatus.activeTuid.isNotNull())
            );
        var nIdle = queryFactory.select(car.count())
            .from(car)
            .where(car.company.eq(company)
                .and(car.mdtStatus.activeTuid.isNull())
            );

        var res = queryFactory
            .select(Projections.constructor(
                CarsStatusResult.class,
                nDriving,
                nIdle
            ))
            .from(car)
            .where(car.company.eq(company))
            .fetchFirst();
        if (res == null) {
            return CarsStatusResult.EMPTY;
        }
        return res;
    }
}
