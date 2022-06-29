package com.javaetmoi.initializr.generator.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.Ordered;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

/**
 * Delete the empty file application.properties previously created by the initializr-generator-spring module.
 */
class DeleteAplicationPropertiesContributor implements ProjectContributor {

    @Override
    public void contribute(Path projectRoot) throws IOException {
        var relativePath = "src/main/resources/application.properties";
        Path output = projectRoot.resolve(relativePath);
        Files.deleteIfExists(output);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
