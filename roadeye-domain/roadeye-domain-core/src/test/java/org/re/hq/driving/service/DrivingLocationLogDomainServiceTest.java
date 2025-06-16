package org.re.hq.driving.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.hq.car.CarFixture;
import org.re.hq.company.CompanyFixture;
import org.re.hq.driving.domain.DrivingLocationLog;
import org.re.hq.driving.dto.DrivingLocationLogCreationCommandFixture;
import org.re.hq.employee.EmployeeFixture;
import org.re.hq.reservation.CarReservationFixture;
import org.re.hq.reservation.domain.CarReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Import({DrivingLocationLogDomainService.class})
@DataJpaTest
class DrivingLocationLogDomainServiceTest {
    @Autowired
    DrivingLocationLogDomainService drivingLocationLogDomainService;

    @Autowired
    EntityManager em;

    CarReservation carReservation;

    @BeforeEach
    void setUp() {
        var company = em.merge(CompanyFixture.create());
        var car = em.merge(CarFixture.create(company));
        var reserver = em.merge(EmployeeFixture.createNormal(company));
        carReservation = em.merge(CarReservationFixture.create(car, reserver, 10, 100));
    }

    @Test
    @DisplayName("첫 위치 이력 생성에 성공해야 한다.")
    void first_location_log_creation_test() {
        var command = DrivingLocationLogCreationCommandFixture.createFirst(carReservation);

        assertDoesNotThrow(() -> {
            drivingLocationLogDomainService.createHistory(command);
        });
    }

    @Test
    @DisplayName("마지막 위치 이력 조회에 성공해야 한다.")
    void last_location_log_retrieval_test() {
        // given
        var nHistories = 10;
        var histories = new DrivingLocationLog[nHistories];
        histories[0] = drivingLocationLogDomainService.createHistory(
            DrivingLocationLogCreationCommandFixture.createFirst(carReservation)
        );
        for (int i = 1; i < nHistories; i++) {
            var command = DrivingLocationLogCreationCommandFixture.create(carReservation, histories[i - 1]);
            histories[i] = drivingLocationLogDomainService.createHistory(command);
        }

        // when
        var lastHistory = drivingLocationLogDomainService.getLastHistory(carReservation);

        // then
        assertNotNull(lastHistory);
        assertEquals(histories[nHistories - 1].getId(), lastHistory.getId());
    }

    @Test
    @DisplayName("이력 갯수가 1개 일 때 마지막 위치 이력 조회에 성공해야 한다.")
    void last_location_log_retrieval_with_one_history_test() {
        // given
        var history = drivingLocationLogDomainService.createHistory(
            DrivingLocationLogCreationCommandFixture.createFirst(carReservation)
        );

        // when
        var lastHistory = drivingLocationLogDomainService.getLastHistory(carReservation);

        // then
        assertNotNull(lastHistory);
        assertEquals(history.getId(), lastHistory.getId());
    }

    @Test
    @DisplayName("예약의 위치 이력 목록을 조회할 때 모든 위치 이력이 조회되야 한다.")
    void find_all_location_logs_of_reservation_test() {
        // given
        var nHistories = 10;
        var histories = new DrivingLocationLog[nHistories];
        histories[0] = drivingLocationLogDomainService.createHistory(
            DrivingLocationLogCreationCommandFixture.createFirst(carReservation)
        );
        for (int i = 1; i < nHistories; i++) {
            var command = DrivingLocationLogCreationCommandFixture.create(carReservation, histories[i - 1]);
            histories[i] = drivingLocationLogDomainService.createHistory(command);
        }

        // when
        var logs = drivingLocationLogDomainService.findAllLogsOfReservation(carReservation);

        // then
        assertEquals(nHistories, logs.size());
    }
}
