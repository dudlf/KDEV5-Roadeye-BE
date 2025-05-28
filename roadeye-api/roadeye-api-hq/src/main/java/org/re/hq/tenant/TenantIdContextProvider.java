package org.re.hq.tenant;

import org.re.hq.tenant.context.TenantIdContext;

public class TenantIdContextProvider implements TenantIdProvider {
    @Override
    public Long getCurrentTenantId() {
        return TenantIdContext.getTenantId().value();
    }
}
