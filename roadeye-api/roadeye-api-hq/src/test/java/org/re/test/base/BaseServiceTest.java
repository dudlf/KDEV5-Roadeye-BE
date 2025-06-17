package org.re.test.base;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.re.security.access.ManagerOnlyHandler;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({
    SecurityAutoConfiguration.class,
    AopAutoConfiguration.class,
    ManagerOnlyHandler.class
})
@ExtendWith({MockitoExtension.class, SpringExtension.class})
public abstract class BaseServiceTest {
}
