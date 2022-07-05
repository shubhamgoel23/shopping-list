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
        //tenantId header
        Parameter tenant = new Parameter();
        tenant.setName("X-Tenant");
        tenant.setIn(ParameterIn.HEADER.toString());
        tenant.required(true);
        Schema<String> tenantSchema = new Schema<>();
        tenantSchema.setType("string");
        tenantSchema.setMaxLength(36);
        tenant.schema(tenantSchema);
        operation.addParametersItem(tenant);
        //customerId header
        Parameter customer = new Parameter();
        customer.setName("X-Customer");
        customer.setIn(ParameterIn.HEADER.toString());
        customer.required(true);
        Schema<String> customerSchema = new Schema<>();
        customerSchema.setType("string");
        customerSchema.setMaxLength(36);
        customer.schema(customerSchema);
        operation.addParametersItem(customer);
        return operation;
    }
}