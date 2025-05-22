package org.re.hq.employee.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.common.BaseEntity;
import org.re.hq.employee.dto.UpdateEmployeeCommand;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee extends BaseEntity {

    @Column(nullable = false)
    private Long tenantId;

    private int tryCount;

    @Embedded
    private EmployeeCredentials credentials;

    @Embedded
    private EmployeeMetadata metadata;

    private Employee(Long tenantId, int tryCount, EmployeeCredentials credentials, EmployeeMetadata metadata) {
        this.tenantId = tenantId;
        this.tryCount = tryCount;
        this.credentials = credentials;
        this.metadata = metadata;
    }

    public static Employee of(Long tenantId, EmployeeCredentials authentication, EmployeeMetadata metadata) {
        return new Employee(tenantId, 0, authentication, metadata);
    }

    public void update(UpdateEmployeeCommand updateEmployeeCommand) {
        metadata.updateName(updateEmployeeCommand.name());
        metadata.updatePosition(updateEmployeeCommand.position());
    }
}
