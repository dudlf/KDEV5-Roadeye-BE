package org.re.hq.employee.domain;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByTenantId(Long tenantId);
}
