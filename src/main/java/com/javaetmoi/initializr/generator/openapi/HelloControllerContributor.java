package com.javaetmoi.initializr.generator.openapi;

import com.javaetmoi.initializr.generator.core.AbstractTemplateClassContributor;

import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;

class HelloControllerContributor extends AbstractTemplateClassContributor {

    public HelloControllerContributor(MustacheTemplateRenderer mustacheTemplateRenderer, ProjectDescription projectDescription) {
        super(mustacheTemplateRenderer, projectDescription);
    }

    @Override
    protected String getJavaFilename() {
        return "controller/HelloController.java";
    }

    @Override
    protected String getMustacheTemplate() {
        return "openapi/HelloController";
    }

    @Override
    protected String getPackage() {
        return ".rest";
    }
}
