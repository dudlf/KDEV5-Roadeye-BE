package org.re.hq.employee;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.domain.EmployeeCredentials;
import org.re.hq.employee.domain.EmployeeMetadata;
import org.re.hq.employee.service.EmployeeDomainService;
import org.re.hq.tenant.TenantIdProvider;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDomainService employeeDomainService;

    public Long createRoot(TenantIdProvider tenantIdProvider, EmployeeCredentials credentials, EmployeeMetadata metadata) {
        employeeDomainService.validateExistsRootAccount(tenantIdProvider.getCurrentTenantId());
        var employee = Employee.createRoot(
            tenantIdProvider.getCurrentTenantId(),
            credentials,
            metadata
        );
        return employeeDomainService.create(employee);
    }

    public Long createNormal(TenantIdProvider tenantIdProvider, EmployeeCredentials credentials, EmployeeMetadata metadata) {
        var employee = Employee.createNormal(
            tenantIdProvider.getCurrentTenantId(),
            credentials,
            metadata
        );
        return employeeDomainService.create(employee);
    }

    public Employee read(TenantIdProvider tenantIdProvider, Long employeeId) {
        return employeeDomainService.read(tenantIdProvider.getCurrentTenantId(), employeeId);
    }
}
