package com.example.tableorder;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Server"),
                @Server(url = "http://52.79.67.165:8080", description = "EC2 API Server")
        }
)

@SpringBootApplication
@EnableScheduling
public class TableOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TableOrderApplication.class, args);
    }

}
