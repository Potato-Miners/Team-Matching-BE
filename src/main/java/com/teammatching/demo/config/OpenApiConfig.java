package com.teammatching.demo.config;


import com.teammatching.demo.web.filter.login.CustomJsonUsernamePasswordAuthenticationFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class OpenApiConfig {

    private final ApplicationContext applicationContext;

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";
    private static final String USERNAME_KEY = "userId";
    private static final String PASSWORD_KEY = "userPassword";

    @Bean
    public GroupedOpenApi defaultAPI() {
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .addOpenApiCustomiser(springSecurityLoginEndpointCustomiser())
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("Team Mon")
                .version("0.5")
                .description("Team Mon 서비스의 API Docs 입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

    @Bean
    public OpenApiCustomiser springSecurityLoginEndpointCustomiser() {
        FilterChainProxy filterChainProxy = applicationContext.getBean(
                AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME,
                FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<CustomJsonUsernamePasswordAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(CustomJsonUsernamePasswordAuthenticationFilter.class::isInstance)
                                .map(CustomJsonUsernamePasswordAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    CustomJsonUsernamePasswordAuthenticationFilter filter = optionalFilter.get();
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperties(USERNAME_KEY, new StringSchema())
                            .addProperties(PASSWORD_KEY, new StringSchema());
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType(
                                    org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                    new MediaType().schema(schema)));
                    operation.requestBody(requestBody);
                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                    operation.responses(apiResponses);
                    operation.addTagsItem("인증 API");
                    operation.summary("로그인").description("아이디와 비밀번호를 받아 로그인합니다.");
                    PathItem pathItem = new PathItem().post(operation);
                    openAPI.getPaths().addPathItem(DEFAULT_LOGIN_REQUEST_URL, pathItem);
                }
            }
        };
    }
}
