package com.k4rnaj1k.bestcafe.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("bearer-key", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"))).addSecurityItem(new SecurityRequirement().addList("bearer-key"))
                .info(new Info().title("k4rnaj1k's cafe").version("v1.0"))
                .servers(List.of(new Server().url("http://localhost:8080/api/v1").description("local server"), new Server().url("http://https://best-cafe.herokuapp.com/api/v1").description("remote heroku server")));
    }
}
