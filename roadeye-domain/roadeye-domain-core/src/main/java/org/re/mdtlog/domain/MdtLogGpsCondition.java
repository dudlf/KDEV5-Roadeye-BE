package org.re.mdtlog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MdtLogGpsCondition {
    // @formatter:off
    NORMAL           ("A"),
    INVALID          ("V"),
    NOT_ATTACHED     ("0"),
    GPS_INFO_INVALID ("P"),
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
