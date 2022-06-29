package com.javaetmoi.initializr.generator.openapi;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;

import io.spring.initializr.generator.condition.ConditionalOnRequestedDependency;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationConfiguration;
import io.spring.initializr.web.autoconfigure.InitializrAutoConfiguration;


import static com.javaetmoi.initializr.generator.core.InitializrConstants.DEPENDENCY_OPENAPI;

@ProjectGenerationConfiguration
@ConditionalOnRequestedDependency(DEPENDENCY_OPENAPI)
@AutoConfigureAfter({InitializrAutoConfiguration.class})
class OpenAPIConfiguration {

    @Bean
    OpenAPIPluginCustomizer openAPIPluginCustomizer(ProjectDescription projectDescription) {
        return new OpenAPIPluginCustomizer(projectDescription);
    }

    @Bean
    OpenApiDependenciesCustomizer openApiDependenciesCustomizer() {
        return new OpenApiDependenciesCustomizer();
    }

    @Bean
    SpecOpenApiContributor specOpenApiContributor() {
        return new SpecOpenApiContributor();
    }

    @Bean
    HelloControllerContributor helloControllerContributor(ProjectDescription projectDescription, MustacheTemplateRenderer mustacheTemplateRenderer) {
        return new HelloControllerContributor(mustacheTemplateRenderer, projectDescription);
    }

    @Bean
    SwaggerControllerContributor swaggerControllerContributor(ProjectDescription projectDescription, MustacheTemplateRenderer mustacheTemplateRenderer) {
        return new SwaggerControllerContributor(mustacheTemplateRenderer, projectDescription);
    }

    @Bean
    RemoveOpenAPIDependencyCustomizer removeOpenAPIDependencyCustomizer() {
        return new RemoveOpenAPIDependencyCustomizer();
    }

    @Bean
    TestOpenApiContributor testOpenApiContributor() {
        return new TestOpenApiContributor();
    }
}
