package com.marekzolek;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ComponentScan(basePackages = "com.marekzolek")
@EnableJpaRepositories(basePackages = "com.marekzolek.repository")
public class TestContext {
}
