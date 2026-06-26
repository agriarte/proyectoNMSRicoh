package com.example.ricoh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RicohNmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RicohNmsApplication.class, args);
    }
}
