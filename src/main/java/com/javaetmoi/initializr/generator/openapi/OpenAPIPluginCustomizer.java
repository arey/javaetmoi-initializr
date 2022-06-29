package com.javaetmoi.initializr.generator.openapi;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

class OpenAPIPluginCustomizer implements BuildCustomizer<MavenBuild> {

    private final ProjectDescription projectDescription;

    OpenAPIPluginCustomizer(ProjectDescription projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(MavenBuild build) {
        build.plugins().add("org.openapitools", "openapi-generator-maven-plugin", c -> {
            c.version("5.4.0");
            c.execution("generate", e -> e.goal("generate"));
            c.configuration(configuration -> {
                configuration.add("inputSpec", "${project.basedir}/src/main/resources/openapi/openapi.yaml");
                configuration.add("generatorName", "spring");
                configuration.add("library", "spring-boot");
                configuration.add("modelNameSuffix", "Resource");
                configuration.add("apiPackage", projectDescription.getPackageName() + ".rest.controller");
                configuration.add("modelPackage", projectDescription.getPackageName() + ".rest.model");
                configuration.configure("configOptions", configOptions -> {
                    configOptions.add("interfaceOnly", "true");
                    configOptions.add("openApiNullable", "false");
                });
            });
        });
    }

}
