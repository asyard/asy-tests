package com.asy.test.configclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
//@RefreshScope
@ConfigurationProperties(prefix = "test")
public class ClientConfiguration {

    @Autowired
    private Environment environment; // alternative to @Value

    //@Value("${value}")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
