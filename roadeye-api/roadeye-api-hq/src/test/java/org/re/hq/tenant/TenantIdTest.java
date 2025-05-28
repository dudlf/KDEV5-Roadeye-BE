package org.re.hq.tenant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.hq.test.controller.TenantIdTestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    value = {TenantIdTestController.class},
    excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public class TenantIdTest {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("TenantId를 정상적으로 받아올 수 있어야 한다")
    public void tenantIdShouldBeResolvedCorrectly() throws Exception {
        var tenantId = 123L;

        mvc.perform(get("/test/tenant-id")
                .header("X-Tenant-Id", tenantId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.tenantId").value(tenantId));
    }
}
