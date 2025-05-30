package org.re.hq.employee;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.domain.EmployeeCredentials;
import org.re.hq.employee.domain.EmployeeMetadata;
import org.re.hq.employee.service.EmployeeDomainService;
import org.re.hq.tenant.TenantId;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDomainService employeeDomainService;

    public Long createRoot(TenantId tenantId, EmployeeCredentials credentials, EmployeeMetadata metadata) {

        employeeDomainService.validateExistsRootAccount(tenantId.value());
        var employee = Employee.createRoot(
                tenantId.value(),
                credentials,
                metadata
        );
        return employeeDomainService.create(employee);
    }

    public Long createNormal(TenantId tenantId, EmployeeCredentials credentials, EmployeeMetadata metadata) {
        var employee = Employee.createNormal(
                tenantId.value(),
                credentials,
                metadata
        );
        return employeeDomainService.create(employee);
    }

    public Employee read(TenantId tenantId, Long employeeId) {
        return employeeDomainService.read(tenantId.value(), employeeId);
    }
}
