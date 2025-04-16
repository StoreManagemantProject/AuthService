package org.example;

import org.example.configuration.EnvConfig;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public EnvConfig envConfig() {
        return new EnvConfig() {
            @Override
            public void loadDotenv() {
                System.setProperty("TEST_PROPERTY", "test-value");
            }
        };
    }
}
