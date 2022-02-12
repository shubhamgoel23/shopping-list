package com.example.shoppinglist.resource.persistance.listener;

import com.example.shoppinglist.resource.context.TenantContext;
import org.hibernate.Session;
import org.hibernate.tuple.ValueGenerator;

public class TenantGenerator implements ValueGenerator<String> {
    @Override
    public String generateValue(Session session, Object o) {
        return TenantContext.getTenantId();
    }
}
