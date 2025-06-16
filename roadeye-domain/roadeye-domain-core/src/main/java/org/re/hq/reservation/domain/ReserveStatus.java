package org.re.hq.reservation.domain;

public enum ReserveStatus {
    REQUESTED,
    APPROVED,
    REJECTED;

    public boolean isRequested() {
        return this == REQUESTED;
    }

    public boolean isRejected() {
        return this == REJECTED;
    }
}
