package com.example.shoppinglist.resource.context;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TenantContext {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    private TenantContext() {

    }

    public static String getTenantId() {
        return CONTEXT.get();

    }

    public static void setTenantId(String tenantId) {
        log.debug("Setting tenant to {}", tenantId);
        CONTEXT.set(tenantId);

    }

    public static void clear() {
        CONTEXT.remove();

    }
}
