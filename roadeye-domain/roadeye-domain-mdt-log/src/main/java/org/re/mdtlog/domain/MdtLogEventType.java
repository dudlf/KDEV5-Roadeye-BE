package org.re.mdtlog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum MdtLogEventType {
    // @formatter:off
    CycleLog("cycle-log", "주기 정보"),
    Ignition("ignition", "시동 이벤트")
    ;
    // @formatter:on

    private final String code;
    private final String description;

    public static MdtLogEventType fromCode(String code) {
        for (MdtLogEventType eventType : MdtLogEventType.values()) {
            if (eventType.getCode().equals(code)) {
                return eventType;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
