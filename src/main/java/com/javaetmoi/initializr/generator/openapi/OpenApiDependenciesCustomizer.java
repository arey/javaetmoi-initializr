package com.javaetmoi.initializr.generator.openapi;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.buildsystem.DependencyScope;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;
import io.spring.initializr.generator.version.VersionReference;

/**
 * Add 2 dependencies: Springdoc and spring-boot-starter-validation required by the openapi-generator-maven-plugin.
 */
class OpenApiDependenciesCustomizer implements BuildCustomizer<MavenBuild> {

    @Override
    public void customize(MavenBuild build) {
        build.dependencies().add("springdoc", Dependency
            .withCoordinates("org.springdoc", "springdoc-openapi-ui")
            .version(VersionReference.ofValue("1.6.9"))
            .build());
        build.dependencies().add("spring-boot-starter-validation", "org.springframework.boot", "spring-boot-starter-validation", DependencyScope.COMPILE);
    }
}
