package org.re.employee;

import org.re.company.domain.Company;
import org.re.employee.domain.Employee;
import org.re.employee.domain.EmployeeCredentials;
import org.re.employee.domain.EmployeeMetadata;

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
