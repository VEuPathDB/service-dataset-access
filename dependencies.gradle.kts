val jersey  = "2.+"
val jackson = "2.11.2"
val junit   = "5.+"
val log4j   = "2.+"

val implementation      by configurations
val runtimeOnly         by configurations
val annotationProcessor by configurations
val testImplementation  by configurations
val testRuntimeOnly     by configurations

dependencies {

  //
  // FgpUtil & Compatibility Dependencies
  //

  // FgpUtil jars
  implementation(files(
    "vendor/fgputil-accountdb-1.0.0.jar",
    "vendor/fgputil-core-1.0.0.jar",
    "vendor/fgputil-db-1.0.0.jar",
    "vendor/fgputil-web-1.0.0.jar"
  ))

  // Compatibility bridge to support the long dead log4j-1.X
  runtimeOnly("org.apache.logging.log4j:log4j-1.2-api:${log4j}")

  // Extra FgpUtil dependencies
  implementation("org.apache.commons:commons-dbcp2:2.+")
  runtimeOnly("org.json:json:20190722")
  runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-json-org:${jackson}")
  runtimeOnly("com.fasterxml.jackson.module:jackson-module-parameter-names:${jackson}")
  runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${jackson}")
  runtimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jackson}")

  //
  // Project Dependencies
  //

  // Oracle
  implementation(files(
    "vendor/ojdbc8.jar",
    "vendor/ucp.jar",
    "vendor/xstreams.jar"
  ))


  // Core lib, prefers local checkout if available
  implementation(/*findProject(":core") ?: */"org.veupathdb.lib:jaxrs-container-core:2.6.0")


  // Jersey
  implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-http:${jersey}")
  implementation("org.glassfish.jersey.containers:jersey-container-grizzly2-servlet:${jersey}")
  implementation("org.glassfish.jersey.media:jersey-media-json-jackson:${jersey}")
  runtimeOnly("org.glassfish.jersey.inject:jersey-hk2:${jersey}")

  // Jackson
  implementation("com.fasterxml.jackson.core:jackson-databind:${jackson}")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jackson}")

  // Log4J
  implementation("org.apache.logging.log4j:log4j-api:${log4j}")
  implementation("org.apache.logging.log4j:log4j-core:${log4j}")
  implementation("org.apache.logging.log4j:log4j:${log4j}")

  // Metrics
  implementation("io.prometheus:simpleclient:0.9.0")
  implementation("io.prometheus:simpleclient_common:0.9.0")

  // Utils
  implementation("io.vulpine.lib:Jackfish:1.+")
  implementation("io.vulpine.lib:sql-import:0.2.1")
  implementation("io.vulpine.lib:lib-query-util:2.0.2")
  implementation("com.devskiller.friendly-id:friendly-id:1.+")
  implementation("javax.mail", "mail", "1.5.0-b01")
  implementation("org.antlr", "ST4", "4.3.1")
  implementation("info.picocli:picocli:4.5.1")
  annotationProcessor("info.picocli:picocli-codegen:4.5.1")


  // Unit Testing
  testImplementation("org.junit.jupiter:junit-jupiter-api:${junit}")
  testImplementation("org.mockito:mockito-core:2.+")
  testImplementation("org.veupathdb.lib.test", "test-utils", "1.1.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junit}")
}
