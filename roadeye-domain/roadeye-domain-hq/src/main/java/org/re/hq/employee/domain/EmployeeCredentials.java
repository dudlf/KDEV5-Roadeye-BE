package org.re.hq.employee.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record EmployeeCredentials(
    String loginId,
    String password
) {

    public static EmployeeCredentials create(String loginId, String password) {
        return new EmployeeCredentials(loginId, password);
    }
}
