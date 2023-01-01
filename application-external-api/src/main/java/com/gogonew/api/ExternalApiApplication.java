package com.gogonew.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // Auditing 기능 적용 위해 추가해야함
@SpringBootApplication
public class ExternalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExternalApiApplication.class, args);
    }

}

