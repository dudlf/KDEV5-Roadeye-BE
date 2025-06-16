package org.re.hq.employee;

import org.re.hq.company.domain.Company;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.domain.EmployeeCredentials;
import org.re.hq.employee.domain.EmployeeMetadata;

public class EmployeeFixture {
    public static Employee createNormal(Company company) {
        return Employee.createNormal(
            company.getId(),
            EmployeeCredentials.create("loginId", "password"),
            EmployeeMetadata.create("name", "password")
        );
    }

    public static Employee createRoot(Company company) {
        return Employee.createNormal(
            company.getId(),
            EmployeeCredentials.create("loginId", "password"),
            EmployeeMetadata.create("name", "password")
        );
    }
}
