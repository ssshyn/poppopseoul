package com.seoulmate.poppopseoul.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("JWT", bearerAuth()));
    }

    public SecurityScheme bearerAuth() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }
//
//
//    @Bean
//    public OpenAPI openAPI() {
//        Server server = new Server()
//                .url(swaggerServerUrl)
//                .description("서버 url");
//
//        return new OpenAPI()
//                .info(new Info().title("poppop seoul")
//                        .description("poppop seoul API")
//                        .version("1.0.0"))
//                .servers(List.of(server))
//                .components(new Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme()
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")
//                        )
//                )
//                .addSecurityItem(
//                        new SecurityRequirement().addList("bearerAuth")
//                );
//    }
}
