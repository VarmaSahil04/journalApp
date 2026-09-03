Important Maven Concepts
POM (Project Object Model)

pom.xml is the main configuration file of a Maven project.

It contains information such as:

Project dependencies
Project version
Plugins
Build configuration
Maven Lifecycle

Some commonly used Maven commands:

mvn validate
mvn compile
mvn test
mvn package
mvn install
Maven Wrapper

This project contains:

mvnw
mvnw.cmd
.mvn/

These files allow the project to use Maven Wrapper commands without requiring a globally installed Maven version.

Example:

./mvnw clean install
