package org.re.mdtlog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.re.common.domain.PersistenceEnum;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MdtLogGpsCondition implements PersistenceEnum<String> {
    // @formatter:off
    NORMAL           ("A"),
    INVALID          ("V"),
    NOT_ATTACHED     ("0"),
    GPS_INFO_INVALID ("P"),
    ;
    // @formatter:on

    private final String code;
}
