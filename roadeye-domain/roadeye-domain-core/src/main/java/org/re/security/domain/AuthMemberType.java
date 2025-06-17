package org.re.security.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthMemberType {
    // @formatter:off
    ADMIN ("Auth:Admin"),
    USER  ("Auth:User"),
    ;
    // @formatter:on

    private final String value;
}
