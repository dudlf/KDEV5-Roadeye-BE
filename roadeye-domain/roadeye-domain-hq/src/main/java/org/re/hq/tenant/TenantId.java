package org.re.hq.tenant;

public record TenantId(
    Long value
) {
    public static final TenantId EMPTY = new TenantId(null);
}
