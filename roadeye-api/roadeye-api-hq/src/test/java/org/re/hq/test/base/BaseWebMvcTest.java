package org.re.hq.test.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.re.hq.config.WebSecurityConfig;
import org.re.hq.security.userdetails.CompanyUserDetailsService;
import org.re.hq.security.userdetails.PlatformAdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@Import({
    AopAutoConfiguration.class,
    WebSecurityConfig.class,
})
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public abstract class BaseWebMvcTest {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    private PlatformAdminUserDetailsService platformAdminUserDetailsService;

    @MockitoBean
    private CompanyUserDetailsService companyUserDetailsService;
}
