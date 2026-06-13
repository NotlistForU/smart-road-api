package com.gabriel.smartroad.config;

import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Configuration
public class SwaggerConfig {
    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(AuthenticationPrincipal.class);
    }
}
