package com.example.readingisgood.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.readingisgood.repository")
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.readingisgood.entity"})
public class PostgreSqlConfig {
}
