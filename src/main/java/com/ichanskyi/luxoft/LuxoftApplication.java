package com.ichanskyi.luxoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LuxoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuxoftApplication.class, args);
    }
}
