package com.javaetmoi.initializr.generator.common;

import org.springframework.core.Ordered;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

/**
 * A {@link SingleResourceProjectContributor} that contributes a
 * {@code application.yaml} file to a project.
 */
class ApplicationYamlContributor extends SingleResourceProjectContributor {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    ApplicationYamlContributor() {
        this("classpath:configuration/application.yml");
    }

    ApplicationYamlContributor(String resourcePattern) {
        super("src/main/resources/application.yml", resourcePattern);
    }
}
