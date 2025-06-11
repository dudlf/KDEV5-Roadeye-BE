package org.re.hq.driving.controller;

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
import org.re.hq.tenant.TenantId;
import org.re.hq.test.security.MockCompanyUserDetails;
import org.re.hq.web.method.support.TenantIdArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class DrivingLogApiTest {
    @Autowired
    MockMvc mockMvc;

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
    @DisplayName("GET /api/driving/drivingLogs/reservation/{reservationId}")
    @MockCompanyUserDetails
    void getDrivingLogsByReservationId() throws Exception {
        var nLogs = 10;
        var logs = new DrivingLocationLog[nLogs];
        logs[0] = em.merge(DrivingLocationLog.create(DrivingLocationLogCreationCommandFixture.createFirst(carReservation)));
        for (int i = 1; i < nLogs; i++) {
            var command = DrivingLocationLogCreationCommandFixture.create(carReservation, logs[i - 1]);
            logs[i] = em.merge(DrivingLocationLog.create(command));
        }
        var tenantId = new TenantId(carReservation.getCar().getCompany().getId());

        // when
        var req = get("/api/driving/drivingLogs/reservation/{reservationId}", carReservation.getId())
            .sessionAttr(TenantIdArgumentResolver.TENANT_ID_SESSION_ATTRIBUTE_NAME, tenantId);

        // then
        mockMvc.perform(req)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.length()").value(nLogs))
            .andExpect(jsonPath("$.data[0].seq").isNumber())
            .andExpect(jsonPath("$.data[0].reservationId").value(carReservation.getId().intValue()))
            .andExpect(jsonPath("$.data[0].latitude").isNumber())
            .andExpect(jsonPath("$.data[0].longitude").isNumber())
            .andExpect(jsonPath("$.data[0].driveTime").isString())
            .andExpect(jsonPath("$.data[0].carSpeed").isNumber())
            .andExpect(jsonPath("$.data[0].historyTime").isString());
    }
}
