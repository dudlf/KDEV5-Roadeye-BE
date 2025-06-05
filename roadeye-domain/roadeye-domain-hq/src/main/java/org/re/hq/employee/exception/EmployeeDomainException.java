package org.re.hq.employee.exception;

import lombok.Getter;
import org.re.hq.domain.exception.DomainExceptionCode;

@Getter
public enum EmployeeDomainException implements DomainExceptionCode {
    // @formatter:off
    ACCOUNT_NOT_FOUND          ("000", "Account not found."),
    ROOT_ACCOUNT_NOT_FOUND     ("100", "Root account not found."),
    ROOT_ACCOUNT_ALREADY_EXISTS("101", "Root account already exists."),
    ;
    // @formatter:on

    private static final String PREFIX = "EMP_";

    private final String code;
    private final String message;

    EmployeeDomainException(String code, String message) {
        this.code = PREFIX + code;
        this.message = message;
    }
}
