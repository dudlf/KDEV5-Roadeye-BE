package org.re.employee.api.payload;

import org.re.employee.domain.Employee;
import org.re.employee.domain.EmployeeRole;

public record EmployeeSearchResponse(
    Long employeeId,
    Long tenantId,
    String loginId,
    String name,
    EmployeeRole role,
    String position
) {

    public static EmployeeSearchResponse from(Employee employee) {
        return new EmployeeSearchResponse(
            employee.getId(),
            employee.getTenantId(),
            employee.getCredentials().loginId(),
            employee.getMetadata().getName(),
            employee.getRole(),
            employee.getMetadata().getPosition()
        );
    }
}
