package com.javaetmoi.initializr.generator;

import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.buildsystem.maven.MavenBuildSystem;
import io.spring.initializr.generator.io.IndentingWriterFactory;
import io.spring.initializr.generator.io.SimpleIndentStrategy;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.language.java.JavaLanguage;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.test.InitializrMetadataTestBuilder;
import io.spring.initializr.generator.test.project.ProjectGeneratorTester;
import io.spring.initializr.generator.test.project.ProjectStructure;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.support.MetadataBuildItemResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.function.Consumer;

import static com.javaetmoi.initializr.generator.core.InitializrConstants.DEPENDENCY_WEB;

public abstract class AbstractInitializrTest {

    protected static final JavaLanguage JAVA = new JavaLanguage();

    protected static final BuildSystem MAVEN = BuildSystem.forId(MavenBuildSystem.ID);

    private static final String DEFAULT_SB_VERSION = "2.7.1";

    private Path tempDir;

    protected static final Dependency WEB = Dependency.withId("web", "org.springframework.boot",
        "spring-boot-starter-web");

    @BeforeEach
    void setup(@TempDir Path dir) {
        this.tempDir = dir;
    }

    protected ProjectStructure generateProject() {
        return generateProject((description) -> {
        });
    }

    protected ProjectStructure generateProject(Consumer<MutableProjectDescription> descriptionCustomizer) {
        InitializrMetadata metadata = InitializrMetadataTestBuilder.withDefaults()
            .addDependencyGroup(DEPENDENCY_WEB, WEB)
            .setMavenParent("org.springframework.boot", "spring-boot-starter-parent", "2.7.1", null, true)
            .build();
        return generateProject(descriptionCustomizer, metadata);
    }

    protected ProjectStructure generateProject(Consumer<MutableProjectDescription> descriptionCustomizer, InitializrMetadata metadata) {
        return generateProject(descriptionCustomizer, metadata,
            (projectGenerationContext) -> {
            });
    }

    private ProjectStructure generateProject(
        Consumer<MutableProjectDescription> descriptionCustomizer, InitializrMetadata metadata,
        Consumer<ProjectGenerationContext> contextCustomizer) {
        ProjectGeneratorTester projectTester = new ProjectGeneratorTester().withDirectory(this.tempDir)
            .withDescriptionCustomizer(
                (description) -> setupProjectDescription(JAVA, DEFAULT_SB_VERSION, MAVEN, description))
            .withDescriptionCustomizer(descriptionCustomizer)
            .withContextInitializer((context) -> setupProjectGenerationContext(metadata, context))
            .withContextInitializer(contextCustomizer);
        return projectTester.generate(new MutableProjectDescription());
    }

    private void setupProjectGenerationContext(InitializrMetadata metadata, ProjectGenerationContext context) {
        context.registerBean(InitializrMetadata.class, () -> metadata);
        context.registerBean(BuildItemResolver.class, () -> new MetadataBuildItemResolver(metadata,
            context.getBean(ProjectDescription.class).getPlatformVersion()));
        context.registerBean(IndentingWriterFactory.class,
            () -> IndentingWriterFactory.create(new SimpleIndentStrategy("\t")));
        context.registerBean(MustacheTemplateRenderer.class,
            () -> new MustacheTemplateRenderer("classpath:/templates"));
    }

    private void setupProjectDescription(Language language, String version, BuildSystem buildSystem,
        MutableProjectDescription description) {
        description.setLanguage(language);
        description.setBuildSystem(buildSystem);
        description.setPlatformVersion(Version.parse(version));
        description.setVersion("2022.1.0-SNAPSHOT");
        description.setPackageName("com.javaetmoi.demo");
        description.setName("demo");
        description.setDescription("Demo project for Spring Boot");
    }

}
