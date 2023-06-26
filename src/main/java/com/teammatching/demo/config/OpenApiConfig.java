package com.teammatching.demo.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("팀 매칭 서비스")
                .version("0.1")
                .description("설명은 뭐라하지");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
