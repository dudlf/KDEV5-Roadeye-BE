package org.re.mdtlog.collector.app.config;

import org.re.mdtlog.collector.app.web.resolver.MdtLogRequestTimeInfoResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MdtLogRequestTimeInfoResolver());
    }
}
