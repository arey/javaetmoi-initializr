# Java&Moi Initializr

[![Java CI with Maven](https://github.com/arey/javaetmoi-initializr/actions/workflows/maven-build.yml/badge.svg)](https://github.com/arey/javaetmoi-initializr/actions/workflows/maven-build.yml)

Custom [Spring Boot Initializr](https://github.com/spring-io/initializr) illustrating the `openapi-generator-maven-plugin` configuration to provide 
a Spring MVC REST API from an OpenAPI 3 Specification and test it with `Swagger UI`.

## Prerequisites

The following items should be installed in your system:
* Java 17+
* Maven 3.6+ (optional if you are using Maven Wrapper)
* Your favorite IDE supporting Spring Initializr
  * [IntelliJ IDEA](https://www.jetbrains.com/idea/)
  * [Eclipse](https://www.eclipse.org/ide/)
  * [VSCode](https://code.visualstudio.com/)
  * [Spring Tools Suite](https://spring.io/tools)


## Run the service

```
git clone https://github.com/arey/javaetmoi-initializr.git
cd javaetmoi-initializr
./mvnw package
java -jar target/javaetmoi-initializr-1.0.0-SNAPSHOT.jar 
```

## Use the service from IntelliJ

* Start IntelliJ IDEA
* Open the menu `File -> New -> Project`
* Select `Spring Initializr`
* Change the server URL from `https://start.spring.io` to `http://localhost:9090`
* Follow the wizard project creation
  * Select the `Web` and `OpenAPI` dependencies
* Open the new project as Maven project
* `Generate Sources and Update Folders for All Project` in the `Maven` view
* Start the `DemoApplication` main class
* In your browser, open the URL `http://localhost:8080`
* Play with the Swagger UI