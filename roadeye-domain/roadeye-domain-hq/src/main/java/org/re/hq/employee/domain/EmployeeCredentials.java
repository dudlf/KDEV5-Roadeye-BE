package org.re.hq.employee.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record EmployeeCredentials(
    String loginId,
    String password
) {
}
