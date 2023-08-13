package com.tellme.tellme.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI()
                .info(getApiInfo())
                .components(getComponents())
                .addSecurityItem(getSecurityItems());
    }

    private Info getApiInfo() {
        return new Info()
                .title("TellMe API 명세서")
                .description("TellMe 서비스 API 명세서입니다.")
                .version("1.0.0");
    }

    private Components getComponents() {
        return new Components()
                .addSecuritySchemes("AccessToken", getJwtSecurityScheme());
    }

    private SecurityScheme getJwtSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-AUTH-TOKEN");
    }

    private SecurityRequirement getSecurityItems() {
        return new SecurityRequirement()
                .addList("AccessToken");
    }


}
