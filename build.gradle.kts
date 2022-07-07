import org.veupathdb.lib.gradle.container.util.Logger.Level

plugins {
  java
  id("org.veupathdb.lib.gradle.container.container-utils") version "3.4.3"
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

// configure VEupathDB container plugin
containerBuild {

  // Change if debugging the build process is necessary.
  logLevel = Level.Trace

  // General project level configuration.
  project {

    // Project Name
    name = "dataset-access-service"

    // Project Group
    group = "org.veupathdb.service"

    // Project Version
    version = "3.0.0"

    // Project Root Package
    projectPackage = "org.veupathdb.service.access"

    // Main Class Name
    mainClassName = "Main"
  }

  // Docker build configuration.
  docker {

    // Docker build context
    context = "."

    // Name of the target docker file
    dockerFile = "Dockerfile"

    // Resulting image tag
    imageName = "dataset-access"

  }

  generateJaxRS {
    // List of custom arguments to use in the jax-rs code generation command
    // execution.
    arguments = listOf(/*arg1, arg2, arg3*/)

    // Map of custom environment variables to set for the jax-rs code generation
    // command execution.
    environment = mapOf(/*Pair("env-key", "env-val"), Pair("env-key", "env-val")*/)
  }

}

tasks.register("print-gen-package") { print("org.veupathdb.service.access") }

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

tasks.shadowJar {
  exclude("**/Log4j2Plugins.dat")
  archiveFileName.set("service.jar")
}

repositories {
  mavenCentral()
  mavenLocal()
  maven {
    name = "GitHubPackages"
    url  = uri("https://maven.pkg.github.com/veupathdb/maven-packages")
    credentials {
      username = if (extra.has("gpr.user")) extra["gpr.user"] as String? else System.getenv("GITHUB_USERNAME")
      password = if (extra.has("gpr.key")) extra["gpr.key"] as String? else System.getenv("GITHUB_TOKEN")
    }
  }
}

//
// Project Dependencies
//

// versions
val coreLib       = "6.7.3"         // Container core lib version
val fgputil       = "2.7.1-jakarta" // FgpUtil version

val jersey        = "3.0.4"       // Jersey/JaxRS version
val jackson       = "2.13.3"      // FasterXML Jackson version
val junit         = "5.8.2"       // JUnit version
val log4j         = "2.17.2"      // Log4J version
val metrics       = "0.15.0"      // Prometheus lib version

// ensures changing modules are never cached
configurations.all {
  resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

dependencies {

  // VEuPathDB libs, prefer local checkouts if available
  implementation(findProject(":core") ?: "org.veupathdb.lib:jaxrs-container-core:${coreLib}")

  // published VEuPathDB libs
  implementation("org.gusdb:fgputil-accountdb:${fgputil}")
  implementation("org.gusdb:fgputil-core:${fgputil}")
  implementation("org.gusdb:fgputil-db:${fgputil}")
  implementation("org.gusdb:fgputil-web:${fgputil}")

  // Jersey
  implementation("org.glassfish.jersey.core:jersey-server:${jersey}")

  // Jackson
  implementation("com.fasterxml.jackson.core:jackson-databind:${jackson}")
  implementation("com.fasterxml.jackson.core:jackson-annotations:${jackson}")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jackson}")

  // Log4J
  implementation("org.apache.logging.log4j:log4j-api:${log4j}")
  implementation("org.apache.logging.log4j:log4j-core:${log4j}")

  // Metrics
  implementation("io.prometheus:simpleclient:${metrics}")
  implementation("io.prometheus:simpleclient_common:${metrics}")
  implementation("org.veupathdb.lib:lib-prometheus-stats:1.1.0")

  // Utils
  implementation("io.vulpine.lib:Jackfish:1.1.0")
  implementation("com.devskiller.friendly-id:friendly-id:1.1.0")
  implementation("io.vulpine.lib:sql-import:0.2.1")
  implementation("io.vulpine.lib:lib-query-util:2.0.2")
  implementation("javax.mail", "mail", "1.5.0-b01")
  implementation("org.antlr", "ST4", "4.3.1")
  implementation("info.picocli:picocli:4.5.1")
  annotationProcessor("info.picocli:picocli-codegen:4.5.1")

  // Unit Testing
  testImplementation("org.junit.jupiter:junit-jupiter-api:${junit}")
  testImplementation("org.mockito:mockito-core:4.6.1")
  testImplementation("org.veupathdb.lib.test", "test-utils", "1.1.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junit}")
}

val test by tasks.getting(Test::class) {
  // Use junit platform for unit tests
  useJUnitPlatform()
}
