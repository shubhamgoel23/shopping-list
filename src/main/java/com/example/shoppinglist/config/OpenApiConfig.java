package com.example.shoppinglist.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@OpenAPIDefinition(info = @Info(title = "Shopping List Service", version = "v1"))
public class OpenApiConfig {

    @Bean
    GroupedOpenApi userApis() { // group all APIs with `user` in the path
        return GroupedOpenApi.builder()
                .group("customer")
                .pathsToMatch("/api/v1/shopping-list/**")
                .addOperationCustomizer(new HeaderConfig())
                .build();
    }

    //    @Bean
    GroupedOpenApi testApis() { // group all APIs with `user` in the path
        return GroupedOpenApi.builder()
                .group("test")
                .addOpenApiCustomiser(new HeaderFilter("X-Tenant"))
                .pathsToMatch("/api/v2/shopping-list/**")
                .build();
    }
}
