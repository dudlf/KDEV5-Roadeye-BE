package org.re.hq.employee.dto;

public record UpdateEmployeeCommand(
    String name,
    String position
) {
}
