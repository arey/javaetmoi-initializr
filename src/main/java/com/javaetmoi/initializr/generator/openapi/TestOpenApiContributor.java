package com.javaetmoi.initializr.generator.openapi;

import io.spring.initializr.generator.project.contributor.SingleResourceProjectContributor;

/**
 * Ajoute le fichier hello.http dans le répertoire test au projet généré.
 */
class TestOpenApiContributor extends SingleResourceProjectContributor {

    TestOpenApiContributor() {
        this("classpath:configuration/openapi/hello.http");
    }
    TestOpenApiContributor(String resourcePattern) {
        super("src/test/resources/http/hello.http", resourcePattern);
    }

}
