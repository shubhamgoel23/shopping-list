package com.example.shoppinglist.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.web.method.HandlerMethod;

public class HeaderConfig implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        Parameter header = new Parameter();
        header.setName("header");
        header.setIn(ParameterIn.HEADER.toString());
        header.required(true);
        Schema<String> schema = new Schema<>();
        schema.setType("string");
        header.schema(schema);
        operation.addParametersItem(header);
        return operation;
    }
}