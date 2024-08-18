package com.cruru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CruruApplication {

    public static void main(String[] args) {
        SpringApplication.run(CruruApplication.class, args);
    }
}
