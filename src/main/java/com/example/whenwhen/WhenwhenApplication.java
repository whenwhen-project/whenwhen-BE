package com.example.whenwhen;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class WhenwhenApplication {
    public static void main(String[] args) {
        SpringApplication.run(WhenwhenApplication.class, args);
    }
}
