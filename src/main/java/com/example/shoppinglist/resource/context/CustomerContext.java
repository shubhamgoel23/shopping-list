package com.example.shoppinglist.resource.context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerContext {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    private CustomerContext() {

    }

    public static void setTenant(String customerId) {
        log.debug("Setting customer id to {}", customerId);
        CONTEXT.set(customerId);

    }

    public static String getCustomerId() {
        return CONTEXT.get();

    }

    public static void clear() {
        CONTEXT.remove();

    }
}
