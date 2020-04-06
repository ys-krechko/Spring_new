package com.it.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Global configuration class for all application
 */
@Configuration
@ComponentScan(basePackages = "com.it")
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:database.properties")
})
@Import({DatabaseConfiguration.class, MessagesConfiguration.class, WebConfiguration.class, SecurityConfiguration.class,
        SwaggerConfig.class, CatchingConfig.class, JacksonConfig.class})
public class AppConfiguration {
}
