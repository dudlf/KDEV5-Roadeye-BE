package org.re.test;

import jakarta.persistence.EntityManager;
import org.re.config.QueryDslConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import({QueryDslConfig.class})
@DataJpaTest
@EnableJpaRepositories
public abstract class BaseQueryDslTest {
    @Autowired
    protected EntityManager em;
}
