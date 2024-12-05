package com.example.whenwhen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WhenwhenApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhenwhenApplication.class, args);
    }
}
