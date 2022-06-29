package com.javaetmoi.initializr.generator.openapi;

import com.javaetmoi.initializr.generator.core.AbstractTemplateClassContributor;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;

public class SwaggerControllerContributor extends AbstractTemplateClassContributor {

    public SwaggerControllerContributor(MustacheTemplateRenderer mustacheTemplateRenderer, ProjectDescription projectDescription) {
        super(mustacheTemplateRenderer, projectDescription);
    }

    @Override
    protected String getJavaFilename() {
        return "SwaggerController.java";
    }

    @Override
    protected String getPackage() {
        return ".rest.controller";
    }

    @Override
    protected String getMustacheTemplate() {
        return "openapi/SwaggerController";
    }


}
