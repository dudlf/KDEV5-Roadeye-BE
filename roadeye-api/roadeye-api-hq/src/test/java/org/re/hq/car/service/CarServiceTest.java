package org.re.hq.car.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.hq.company.service.CompanyService;
import org.re.hq.employee.domain.EmployeeRole;
import org.re.hq.tenant.TenantId;
import org.re.hq.test.base.BaseServiceTest;
import org.re.hq.test.security.MockCompanyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(CarService.class)
class CarServiceTest extends BaseServiceTest {
    @Autowired
    CarService carService;

    @MockitoBean
    CompanyService companyService;

    @MockitoBean
    CarDomainService carDomainService;

    @Test
    @MockCompanyUserDetails(role = EmployeeRole.NORMAL)
    @DisplayName("일반 사용자는 차량을 삭제할 수 없다.")
    void testCarService() {
        var tenantId = new TenantId(111L);
        var carId = 1L;

        assertThrows(AccessDeniedException.class, () -> {
            carService.deleteCar(tenantId, carId);
        });
    }

    @Test
    @MockCompanyUserDetails(role = EmployeeRole.ROOT)
    @DisplayName("관리자 권한을 가진 사용자는 차량을 삭제할 수 있다.")
    void testManagerCanDeleteCar() {
        var tenantId = new TenantId(111L);
        var carId = 1L;

        assertDoesNotThrow(() -> {
            carService.deleteCar(tenantId, carId);
        });
    }
}
