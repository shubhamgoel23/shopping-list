package com.example.shoppinglist.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OpenApiConfig {

    @Bean
    GroupedOpenApi userApis() { // group all APIs with `user` in the path
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/api/v1/shopping-list/**")
                .addOpenApiCustomiser(new HeaderFilter("X-Tenant"))
                .addOperationCustomizer(new HeaderConfig())
                .build();
    }

    @Bean
    GroupedOpenApi testApis() { // group all APIs with `user` in the path
        return GroupedOpenApi.builder()
                .group("test")
                .pathsToMatch("/api/v2/shopping-list/**")
                .build();
    }
}
