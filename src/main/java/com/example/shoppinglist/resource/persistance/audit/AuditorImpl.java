package com.example.shoppinglist.resource.persistance.audit;

import com.example.shoppinglist.resource.context.CustomerContext;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(CustomerContext.getCustomerId());
    }

}