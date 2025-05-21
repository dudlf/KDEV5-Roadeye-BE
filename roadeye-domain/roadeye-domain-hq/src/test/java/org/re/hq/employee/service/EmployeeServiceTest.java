package org.re.hq.employee.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.re.hq.employee.domain.EmployeeCredentials;
import org.re.hq.employee.domain.EmployeeRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void 루트_계정을_생성합니다() {
        var credentials = new EmployeeCredentials("root", "root");
        var employee = employeeService.createRootAccount(1L, credentials, "root", "root");

        assertThat(employee.getMetadata().getRole()).isEqualTo(EmployeeRole.ROOT);
    }

    @Test
    void 루트_계정은_하나만_존재합니다() {
        var credentials = new EmployeeCredentials("root", "root");
        employeeService.createRootAccount(1L, credentials, "root", "root");

        assertThatThrownBy(() -> employeeService.createRootAccount(1L, credentials, "root", "root"))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 일반_계정을_생성합니다() {
        var credentials = new EmployeeCredentials("root", "root");
        var employee = employeeService.createNormalAccount(1L, credentials, "root", "root");

        assertThat(employee.getMetadata().getRole()).isEqualTo(EmployeeRole.NORMAL);
    }
}
