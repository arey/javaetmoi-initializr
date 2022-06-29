/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific JAVA governing permissions and
 * limitations under the License.
 */

package com.javaetmoi.initializr.generator.common;

import com.javaetmoi.initializr.generator.AbstractInitializrTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.test.project.JvmModuleAssert;
import io.spring.initializr.generator.test.project.ModuleAssert;
import io.spring.initializr.generator.test.project.ProjectStructure;

/**
 * Code compliance tests.
 */
class CommonSpringBootTest extends AbstractInitializrTest {

    @Test
    void currentGenerationJar() {
        // Given

        // When
        ProjectStructure project = generateProject();

        // Then
        ModuleAssert moduleAssert = Assertions.assertThat(project);
        moduleAssert.filePaths()
            .hasSize(9)
            .containsExactlyInAnyOrder(
                ".gitignore",
                ".mvn/wrapper/maven-wrapper.jar",
                ".mvn/wrapper/maven-wrapper.properties",
                "mvnw",
                "mvnw.cmd",
                "pom.xml",
                "src/main/java/com/javaetmoi/demo/DemoApplication.java",
                "src/test/java/com/javaetmoi/demo/DemoApplicationTests.java",
                "src/main/resources/application.yml");
        moduleAssert.file( "src/main/resources/application.yml")
            .content()
            .contains("Spring Boot Yaml configuration file");
    }

    @Test
    void should_project_has_spring_boot_starter_parent_has_parent_pom() {
        // Given

        // When
        ProjectStructure project = generateProject();

        // Then
        Assertions.assertThat(project).mavenBuild()
            .hasParent("org.springframework.boot", "spring-boot-starter-parent", "2.7.1");
    }


    @Test
    void currentGenerationWar() {
        ProjectStructure project = generateProject((description) -> description.setPackaging(Packaging.forId("war")));
        Assertions.assertThat(project).filePaths()
            .hasSize(10)
            .contains(
                ".gitignore",
                ".mvn/wrapper/maven-wrapper.jar",
                ".mvn/wrapper/maven-wrapper.properties",
                "mvnw",
                "mvnw.cmd",
                "pom.xml",
                "src/main/java/com/javaetmoi/demo/DemoApplication.java",
                "src/main/java/com/javaetmoi/demo/ServletInitializer.java",
                "src/test/java/com/javaetmoi/demo/DemoApplicationTests.java",
                "src/main/resources/application.yml");
    }

    @Test
    void currentGenerationMainClass() {
        ProjectStructure project = generateProject();
        Assertions.assertThat(project).asJvmModule(JAVA).mainSource("com.javaetmoi.demo", "DemoApplication")
            .hasSameContentAs(new ClassPathResource("project/" + JAVA + "/javaetmoi/DemoApplication." + getExpectedExtension()));
    }

    @Test
    void currentGenerationTestClass() {
        ProjectStructure project = generateProject();
        Assertions.assertThat(project).asJvmModule(JAVA).testSource("com.javaetmoi.demo", "DemoApplicationTests")
            .hasSameContentAs(new ClassPathResource("project/" + JAVA + "/javaetmoi/DemoApplicationTests." + getExpectedExtension()));
    }

    @Test
    void currentGenerationServletInitializer() {
        ProjectStructure project = generateProject( (description) -> description.setPackaging(Packaging.forId("war")));
        Assertions.assertThat(project).asJvmModule(JAVA).mainSource("com.javaetmoi.demo", "ServletInitializer")
            .hasSameContentAs(new ClassPathResource("project/" + JAVA + "/javaetmoi/" + "ServletInitializer." + getExpectedExtension()));
    }

    @Test
    void currentGenerationCustomCoordinates() {
        ProjectStructure project = generateProject((description) -> {
            description.setGroupId("com.javaetmoi.acme");
            description.setArtifactId("my-project");
            description.setPackageName("com.javaetmoi.acme.myproject");
            description.setApplicationName("MyProjectApplication");
        });
        JvmModuleAssert jvmModuleAssert = Assertions.assertThat(project).asJvmModule(JAVA);
        jvmModuleAssert
            .mainSource("com.javaetmoi.acme.myproject", "MyProjectApplication")
            .hasSameContentAs(new ClassPathResource("project/" + JAVA + "/javaetmoi/MyProjectApplication." + getExpectedExtension()));
        jvmModuleAssert.testSource("com.javaetmoi.acme.myproject", "MyProjectApplicationTests")
            .hasSameContentAs(new ClassPathResource("project/" + JAVA + "/javaetmoi/MyProjectApplicationTests." + getExpectedExtension()));
    }

    private String getExpectedExtension() {
        return JAVA.sourceFileExtension() + ".gen";
    }

}
