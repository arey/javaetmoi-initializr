package com.javaetmoi.initializr.generator.openapi;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

/**
 * Ajoute le fichier openapi.yaml au projet généré.
 */
class SpecOpenApiContributor extends SingleResourceProjectContributor {

    SpecOpenApiContributor() {
        this("classpath:configuration/openapi/openapi.yaml");
    }
    SpecOpenApiContributor(String resourcePattern) {
        super("src/main/resources/openapi/openapi.yaml", resourcePattern);
    }

}
