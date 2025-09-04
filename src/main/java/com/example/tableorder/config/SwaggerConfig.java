package com.example.tableorder.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Table Order API")
                        .description("테이블 주문 시스템을 위한 REST API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Table Order Team")
                                .email("contact@tableorder.com")
                                .url("https://github.com/hanium-table-order/Order-Free-Backend"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Development Server"),
                        new Server().url("https://api.tableorder.com").description("Production Server")
                ));
    }
}
