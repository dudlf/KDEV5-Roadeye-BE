package org.re.hq.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.re.hq.car.CarFixture;
import org.re.hq.car.domain.Car;
import org.re.hq.company.CompanyFixture;
import org.re.hq.employee.EmployeeFixture;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.domain.EmployeeRole;
import org.re.hq.reservation.CarReservationFixture;
import org.re.hq.reservation.api.CarReservationApi;
import org.re.hq.reservation.dto.CarReservationResponse;
import org.re.hq.reservation.service.CarReservationService;
import org.re.hq.security.access.ManagerOnlyHandler;
import org.re.hq.tenant.TenantId;
import org.re.hq.test.security.MockCompanyUserDetails;
import org.re.hq.web.method.support.TenantIdArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({AopAutoConfiguration.class, ManagerOnlyHandler.class})
@WebMvcTest(CarReservationApi.class)
@AutoConfigureMockMvc
class CarReservationApiTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarReservationService carReservationService;


    @Nested
    @DisplayName("조회 테스트")
    class ReadTest {
        @Test
        @DisplayName("차량 예약 목록 조회")
        @MockCompanyUserDetails
        void carReservationListTest() throws Exception {
            TenantId tenantId = new TenantId(111L);
            var pageable = PageRequest.of(0, 10);
            var company = CompanyFixture.create();
            var car = Mockito.spy(CarFixture.create()); // 실제 Car 객체 생성
            var employee = Mockito.spy(EmployeeFixture.createNormal(company));
            var reservationResponse = Mockito.spy(CarReservationFixture.create(car,employee,1,5));
            Page<CarReservationResponse> mockPage =
                new PageImpl<>(List.of(CarReservationResponse.from(reservationResponse)), pageable, 1);

            Mockito.when(carReservationService.findByTenantId(Mockito.any(), Mockito.any(Pageable.class)))
                .thenReturn(mockPage);

            var req = get("/api/reservation")
                .param("page", "0")
                .param("size", "10")
                .sessionAttr(TenantIdArgumentResolver.TENANT_ID_SESSION_ATTRIBUTE_NAME, tenantId)
                .with(csrf());

            // then
            mvc.perform(req)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].reserveStatus").value("REQUESTED"));
        }
    }

    @Nested
    @DisplayName("수정 테스트")
    class CreateTest {
        @Test
        @DisplayName("일반 사용자는 예약을 승인할 수 없다.")
        @MockCompanyUserDetails(role = EmployeeRole.NORMAL)
        void approveCarReservationTest() throws Exception {
            Long reservationId = 10L;
            TenantId tenantId = new TenantId(1L);

            var req = patch("/api/reservation/{reservationId}/approve", reservationId)
                .contentType("application/json")
                .sessionAttr(TenantIdArgumentResolver.TENANT_ID_SESSION_ATTRIBUTE_NAME, tenantId)
                .with(csrf());

            mvc.perform(req)
                .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("루트 사용자는 예약을 승인할 수 있다.")
        @MockCompanyUserDetails(role = EmployeeRole.ROOT)
        void managerApproveCarReservationTest() throws Exception {
            Long reservationId = 10L;
            TenantId tenantId = new TenantId(1L);

            var req = patch("/api/reservation/{reservationId}/approve", reservationId)
                .contentType("application/json")
                .sessionAttr(TenantIdArgumentResolver.TENANT_ID_SESSION_ATTRIBUTE_NAME, tenantId)
                .with(csrf());

            mvc.perform(req)
                .andExpect(status().isOk());
        }
    }

}
