import java.util.Properties
import java.io.FileInputStream

plugins {
  java
  id("org.veupathdb.lib.gradle.container.container-utils") version "1.0.2"
}

repositories {
  mavenCentral()
  maven {
    name = "GitHubPackages"
    url  = uri("https://maven.pkg.github.com/veupathdb/maven-packages")
    credentials {
      username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
      password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
    }
  }
}

java {
  targetCompatibility = JavaVersion.VERSION_15
  sourceCompatibility = JavaVersion.VERSION_15
}

containerBuild {
  setFgpUtilVersion("14aa44a13c28257b702a98ddbecdf1e72812e2e6");
}

apply(from = "dependencies.gradle.kts")

// Load Props
val buildProps = Properties()
buildProps.load(FileInputStream(File(rootDir, "service.properties")))
val fullPack = "${buildProps["app.package.root"]}.${buildProps["app.package.service"]}"

// Project settings
group = buildProps["project.group"] ?: error("empty 1")
version = buildProps["project.version"] ?: error("empty 2")


tasks.jar {
  manifest {
    attributes["Main-Class"] = "${fullPack}.${buildProps["app.main-class"]}"
    attributes["Implementation-Title"] = buildProps["project.name"]
    attributes["Implementation-Version"] = buildProps["project.version"]
  }
  println("Packaging Components")
  from(configurations.runtimeClasspath.get().map {
    println("  " + it.name)

    if (it.isDirectory) it else zipTree(it).matching {
      exclude { f ->
        val name = f.name.toLowerCase()
        (name.contains("log4j") && name.contains(".dat")) ||
          name.endsWith(".sf") ||
          name.endsWith(".dsa") ||
          name.endsWith(".rsa")
      } } })
  archiveFileName.set("service.jar")
}

tasks.register("print-package") { print(fullPack) }
tasks.register("print-container-name") { print(buildProps["container.name"]) }

val test by tasks.getting(Test::class) {
  // Use junit platform for unit tests
  useJUnitPlatform()
}
