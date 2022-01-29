package com.example.shoppinglist.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomiser;

public class HeaderFilter implements OpenApiCustomiser {

    private final String headerName;

    public HeaderFilter(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public void customise(OpenAPI openApi) {
//        check all values here to make api groups based on http methods
//        openApi.getPaths().entrySet().stream().map()
        openApi.getPaths().entrySet().removeIf(path -> path.getValue().readOperations().stream()
                .anyMatch(operation -> operation.getParameters() == null
                        || operation.getParameters().stream().noneMatch(
                        parameter -> ParameterIn.HEADER.toString().equals(parameter.getIn())
                                && parameter.getName().equals(headerName)
                )));
    }
}