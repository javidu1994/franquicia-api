package co.com.accenture.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.r2dbc.pool")
public record PostgreSQLConnectionPoolProperties(
        int initialSize,
        int maxSize,
        int maxIdleTime) {
}
