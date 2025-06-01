package org.re.hq.car.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.re.hq.car.CarFixture;
import org.re.hq.car.service.CarService;
import org.re.hq.test.security.MockCompanyUserDetails;
import org.re.hq.web.method.support.TenantIdArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarApi.class)
class CarApiTest {
    @Autowired
    MockMvc mvc;

    @MockitoBean
    CarService carService;

    @Nested
    @DisplayName("조회 테스트")
    class ReadTest {
        @Test
        @DisplayName("차량 목록 조회")
        @MockCompanyUserDetails
        void car_list_test() throws Exception {
            // given
            var nCars = 10;
            var cars = CarFixture.createList(nCars);
            var page = new PageImpl<>(cars);
            Mockito.when(carService.getCars(Mockito.any(), Mockito.any()))
                .thenReturn(page);

            // when
            var req = get("/api/cars")
                .param("tenantId", "111")
                .param("page", "0")
                .param("size", "10")
                .sessionAttr(TenantIdArgumentResolver.TENANT_ID_SESSION_ATTRIBUTE_NAME, 111L);

            // then
            mvc.perform(req)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(nCars))
                .andExpect(jsonPath("$.data[*].id").exists());
        }
    }
}
