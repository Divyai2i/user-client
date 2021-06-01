package com.userModule.config;

import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.MeterRegistry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class ActuatorConfig {

    @Autowired

    private DataSource dataSource;

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    DataSourceStatusProbe dataSourceStatusProbe(DataSource dataSource) {
        return new DataSourceStatusProbe(dataSource);
    }
    
    @Bean
    private MeterFilter excludeTomcatFilter() {
        return MeterFilter.denyNameStartsWith("tomcat");
    }
}
