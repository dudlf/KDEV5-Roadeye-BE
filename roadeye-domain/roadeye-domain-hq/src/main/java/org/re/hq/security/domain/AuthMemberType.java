package org.re.hq.security.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthMemberType {
    // @formatter:off
    ADMIN ("Admin"),
    USER  ("User"),
    ;
    // @formatter:on

    private final String value;
}
