package org.re.mdtlog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MdtLogGpsCondition {
    // @formatter:off
    Normal         ("A"),
    InValid        ("V"),
    NotAttached    ("0"),
    GPSInfoInvalid ("P"),
    ;
    // @formatter:on

    private final String code;

    public static MdtLogGpsCondition of(String code) {
        for (MdtLogGpsCondition condition : values()) {
            if (condition.getCode().equals(code)) {
                return condition;
            }
        }
        throw new IllegalArgumentException("Invalid GPS condition code: " + code);
    }
}
