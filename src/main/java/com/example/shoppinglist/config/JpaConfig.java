package com.example.shoppinglist.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.example.shoppinglist.resource.persistance.entity")
@EnableJpaRepositories(basePackages = "com.example.shoppinglist.resource.persistance.repository")
@EnableJpaAuditing
public class JpaConfig {
}