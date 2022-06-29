package com.javaetmoi.initializr.generator.openapi;

import java.util.function.Consumer;

import com.javaetmoi.initializr.generator.AbstractInitializrTest;
import org.junit.jupiter.api.Test;

import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.test.InitializrMetadataTestBuilder;
import io.spring.initializr.metadata.Dependency;


import static com.javaetmoi.initializr.generator.core.InitializrConstants.DEPENDENCY_OPENAPI;
import static org.assertj.core.api.Assertions.assertThat;

class OpenApiTest extends AbstractInitializrTest {

    protected static final Dependency OPENAPI = Dependency.withId(DEPENDENCY_OPENAPI, "groupId", "artefactId");

    private static final Consumer<MutableProjectDescription> DESCRIPTION = d -> d.addDependency(
        OPENAPI.getId(),
        io.spring.initializr.generator.buildsystem.Dependency.withCoordinates(OPENAPI.getGroupId(), OPENAPI.getArtifactId())
            .build());

    @Test
    void should_openapi_dependency_generate_4_files() {
        // Given
        var metadata = InitializrMetadataTestBuilder.withDefaults().build();

        // When
        var project = generateProject( DESCRIPTION, metadata);

        // Then
        assertThat(project).filePaths()
            .contains(
                "src/main/java/com/javaetmoi/demo/rest/controller/HelloController.java",
                "src/main/java/com/javaetmoi/demo/rest/controller/SwaggerController.java",
                "src/main/resources/openapi/openapi.yaml",
                "src/test/resources/http/hello.http");
    }

    @Test
    void should_openapi_dependency_generate_pom_with_springdoc_and_spring_boot_starter_validation() {
        // Given
        var metadata = InitializrMetadataTestBuilder.withDefaults().build();

        // When
        var project = generateProject(DESCRIPTION, metadata);

        // Then
        assertThat(project).mavenBuild()
            .hasDependency("org.springdoc", "springdoc-openapi-ui")
            .hasDependency("org.springframework.boot", "spring-boot-starter-validation");
    }

    @Test
    void should_openapi_dependency_configure_openapi_maven_plugin() {
        // Given
        var metadata = InitializrMetadataTestBuilder.withDefaults().build();

        // When
        var project = generateProject(DESCRIPTION, metadata);

        // Then
        assertThat(project).mavenBuild()
            .containsIgnoringWhitespaces(
                """
                        <plugin>
                            <groupId>org.openapitools</groupId>
                            <artifactId>openapi-generator-maven-plugin</artifactId>
                            <version>5.4.0</version>
                            <configuration>
                                <inputSpec>${project.basedir}/src/main/resources/openapi/openapi.yaml</inputSpec>
                                <generatorName>spring</generatorName>
                                <library>spring-boot</library>
                                <modelNameSuffix>Resource</modelNameSuffix>
                                <apiPackage>com.javaetmoi.demo.rest.controller</apiPackage>
                                <modelPackage>com.javaetmoi.demo.rest.model</modelPackage>
                                <configOptions>
                                    <interfaceOnly>true</interfaceOnly>
                                    <openApiNullable>false</openApiNullable>
                                </configOptions>
                            </configuration>
                            <executions>
                                <execution>
                                    <id>generate</id>
                                    <goals>
                                        <goal>generate</goal>
                                    </goals>
                                </execution>
                            </executions>
                        </plugin>
                    """
            );
    }

    @Test
    void should_openapi_dependency_generate_HelloController() {
        // Given
        var metadata = InitializrMetadataTestBuilder.withDefaults().build();

        // When
        var project = generateProject(DESCRIPTION, metadata);

        // Then
        // @formatter:off
        assertThat(project).textFile("src/main/java/com/javaetmoi/demo/rest/controller/HelloController.java").containsExactly(
            "package com.javaetmoi.demo.rest.controller;",
            "",
            "import org.springframework.http.ResponseEntity;",
            "import org.springframework.web.bind.annotation.RequestMapping;",
            "import org.springframework.web.bind.annotation.RestController;",
            "",
            "import com.javaetmoi.demo.rest.model.MessageResource;",
            "",
            "@RestController",
            "@RequestMapping(\"/api/v1\")",
            "public class HelloController implements HelloApi {",
            "",
            "    @Override",
            "    public ResponseEntity<MessageResource> hello(String name) {",
            "        return ResponseEntity.ok(new MessageResource().message(name));",
            "    }",
            "}"
        );
        // @formatter:on
    }
}
