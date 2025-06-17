package org.re.mdtlog.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MdtLogEventType {
    // @formatter:off
    CYCLE_LOG  ("cycle-log", "주기 정보"),
    IGNITION   ("ignition", "시동 이벤트")
    ;
    // @formatter:on

    private final String code;
    private final String description;

    public static MdtLogEventType from(String code) {
        for (MdtLogEventType eventType : MdtLogEventType.values()) {
            if (eventType.getCode().equals(code)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
