package org.sqlTool.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties("spring.datasource")
public class JdbcConfig {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
