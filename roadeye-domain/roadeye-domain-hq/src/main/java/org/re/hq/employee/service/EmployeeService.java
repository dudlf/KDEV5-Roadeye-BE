package org.re.hq.employee.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.employee.domain.Employee;
import org.re.hq.employee.domain.EmployeeCredentials;
import org.re.hq.employee.domain.EmployeeMetadata;
import org.re.hq.employee.domain.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * 업체 계정의 루트 계정을 생성하는 기능
     * <p>
     * 업체의 루트 계정은 하나만 존재해야 함
     */
    public Employee createRootAccount(Long tenantId, EmployeeCredentials credentials, String name, String position) {
        if (employeeRepository.existsByTenantId(tenantId)) {
            // TODO 도메인 객체 예외 처리로 바꿀 것
            throw new IllegalStateException("Root account already exists");
        }

        var employee = Employee.of(tenantId, credentials, EmployeeMetadata.createRoot(name, position));

        return employeeRepository.save(employee);
    }

    /**
     * 일반 계정을 생성하는 도메인 서비스
     */
    public Employee createNormalAccount(Long tenantId, EmployeeCredentials credentials, String name, String position) {
        var employee = Employee.of(tenantId, credentials, EmployeeMetadata.createNormal(name, position));

        return employeeRepository.save(employee);
    }

}
