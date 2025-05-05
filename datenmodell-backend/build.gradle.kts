import io.gitlab.arturbosch.detekt.Detekt

plugins {
  alias(libs.plugins.kotlinSpring)
  alias(libs.plugins.kotlinJpa)
  alias(libs.plugins.springBoot)
  alias(libs.plugins.springDependencyManagement)
  alias(libs.plugins.openApiGenerator)
}

group = "org.datenmodell"

version = "0.0.1-SNAPSHOT"

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

repositories { mavenCentral() }

dependencies {
  implementation(libs.springBootStarterJpa)
  implementation(libs.springBootStarterWeb)
  implementation(libs.kotlinJackson)
  implementation(libs.springBootStarterOauth2)
  implementation(libs.springBootStarterOauth2Client)
  implementation(libs.springBootStarterSecurity)
  implementation(libs.kotlinReflect)
  implementation(libs.swaggerCore)
  implementation(libs.validationApi)
  implementation(libs.googleOauth2)
  implementation(libs.nimbusJoseJwt)
  implementation(libs.springWeb)
  implementation(libs.jacksonDatabind)
  implementation(libs.jacksonDatabindNullable)
  implementation(libs.jacksonDatatype)
  implementation(libs.jakartaAnnotation)
  implementation(libs.flywayCore)
  implementation(libs.flywayPostgres)
  developmentOnly(libs.springDevTools)
  compileOnly(libs.servletApi)
  runtimeOnly(libs.postgresDriver)
  testImplementation(libs.springBootTest)
  testImplementation(libs.kotlinJunit5)
  testImplementation(libs.jupiter)
  testRuntimeOnly(libs.junitLauncher)
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.compileKotlin {
  dependsOn("openApiGenerate")
  dependsOn("generateClients")
}

tasks.compileJava {
  dependsOn("openApiGenerate")
  dependsOn("generateClients")
}

openApiGenerate {
  inputSpec.set("$rootDir/datenmodell-backend/src/main/resources/backendOpenApi.yaml")
  generatorName.set("kotlin-spring")
  configOptions.set(
      mapOf(
          "interfaceOnly" to "true",
      ),
  )
}

tasks.register("generateClients") {
  description = "Task to generate all required clients for the service."
  group = "clients"
  dependsOn("generateEurodatAppClient")
  dependsOn("generateEurodatControllerClient")
  dependsOn("generateEurodatDataManagementClient")
  dependsOn("generateEurodatDatabaseClient")
  dependsOn("generateNfdi4HealthClient")
}

tasks.register(
    "generateEurodatAppClient",
    org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
  description = "Task to generate clients for the EuroDaT app service."
  group = "clients"
  val eurodatClientDestinationPackage = "org.eurodat.eurodatapp.openApiClient"
  input = project.file("$rootDir/datenmodell-backend/src/main/resources/eurodatApp.yaml").path
  outputDir.set(
      layout.buildDirectory.dir("clients/eurodat-app").get().toString(),
  )
  packageName.set(eurodatClientDestinationPackage)
  modelPackage.set("$eurodatClientDestinationPackage.model")
  apiPackage.set("$eurodatClientDestinationPackage.api")
  generatorName.set("java")
  library.set("restclient")
  validateSpec.set(false)

  configOptions.set(
      mapOf(
          "dateLibrary" to "java8",
          "useJakartaEe" to "true",
      ),
  )
}

tasks.register(
    "generateEurodatControllerClient",
    org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
  description = "Task to generate clients for the EuroDaT controller service."
  group = "clients"
  val eurodatClientDestinationPackage = "org.eurodat.eurodatcontroller.openApiClient"
  input =
      project.file("$rootDir/datenmodell-backend/src/main/resources/eurodatController.yaml").path
  outputDir.set(
      layout.buildDirectory.dir("clients/eurodat-controller").get().toString(),
  )
  packageName.set(eurodatClientDestinationPackage)
  modelPackage.set("$eurodatClientDestinationPackage.model")
  apiPackage.set("$eurodatClientDestinationPackage.api")
  generatorName.set("java")
  library.set("restclient")
  validateSpec.set(false)

  configOptions.set(
      mapOf(
          "dateLibrary" to "java8",
          "useJakartaEe" to "true",
      ),
  )
}

tasks.register(
    "generateEurodatDataManagementClient",
    org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
  description = "Task to generate clients for the EuroDaT data management service."
  group = "clients"
  val eurodatClientDestinationPackage = "org.eurodat.eurodatdatamanagement.openApiClient"
  input =
      project
          .file("$rootDir/datenmodell-backend/src/main/resources/eurodatDataManagement.yaml")
          .path
  outputDir.set(
      layout.buildDirectory.dir("clients/eurodat-data-management").get().toString(),
  )
  packageName.set(eurodatClientDestinationPackage)
  modelPackage.set("$eurodatClientDestinationPackage.model")
  apiPackage.set("$eurodatClientDestinationPackage.api")
  generatorName.set("java")
  library.set("restclient")
  validateSpec.set(false)

  configOptions.set(
      mapOf(
          "dateLibrary" to "java8",
          "useJakartaEe" to "true",
      ),
  )
}

tasks.register(
    "generateEurodatDatabaseClient",
    org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
  description = "Task to generate clients for the EuroDaT database service."
  group = "clients"
  val eurodatClientDestinationPackage = "org.eurodat.eurodatdatabase.openApiClient"
  input = project.file("$rootDir/datenmodell-backend/src/main/resources/eurodatDatabase.yaml").path
  outputDir.set(
      layout.buildDirectory.dir("clients/eurodat-database").get().toString(),
  )
  packageName.set(eurodatClientDestinationPackage)
  modelPackage.set("$eurodatClientDestinationPackage.model")
  apiPackage.set("$eurodatClientDestinationPackage.api")
  generatorName.set("java")
  library.set("restclient")
  validateSpec.set(false)

  configOptions.set(
      mapOf(
          "dateLibrary" to "java8",
          "useJakartaEe" to "true",
      ),
  )
}

tasks.register(
    "generateNfdi4HealthClient",
    org.openapitools.generator.gradle.plugin.tasks.GenerateTask::class,
) {
  description = "Task to generate clients for the nfdi4health service."
  group = "clients"
  val nfdi4HealthClientDestinationPackage = "org.nfdi4health"
  input = project.file("$rootDir/datenmodell-backend/src/main/resources/nfdi4health.yaml").path
  outputDir.set(
      layout.buildDirectory.dir("clients/nfdi4health").get().toString(),
  )
  packageName.set(nfdi4HealthClientDestinationPackage)
  modelPackage.set("$nfdi4HealthClientDestinationPackage.model")
  apiPackage.set("$nfdi4HealthClientDestinationPackage.api")
  generatorName.set("java")
  library.set("restclient")

  configOptions.set(
      mapOf(
          "dateLibrary" to "java8",
          "useJakartaEe" to "true",
          "basePath" to "https://health-study-hub.de",
      ),
  )
}

// requires postgres db to be active
tasks.register<Test>("setupTestData") { useJUnitPlatform { includeTags("database-setup") } }

sourceSets {
  val main by getting
  main.kotlin.srcDir(layout.buildDirectory.dir("generate-resources/main/src/main/kotlin"))
  main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-app/src/main/java"))
  main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-controller/src/main/java"))
  main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-data-management/src/main/java"))
  main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-database/src/main/java"))
  main.java.srcDir(layout.buildDirectory.dir("clients/nfdi4health/src/main/java"))
}

tasks.test { useJUnitPlatform { excludeTags("eurodat", "database-setup") } }

detekt {
  ignoreFailures = true // TODO temporarily, to be removed
  buildUponDefaultConfig = true // preconfigure defaults
  allRules = false // activate all available (even unstable) rules.
}

tasks.withType<Detekt>().configureEach {
  reports {
    html.required.set(true)
    xml.required.set(false)
    sarif.required.set(true)
    md.required.set(false)
  }
}

tasks.withType<Detekt>().configureEach { jvmTarget = "1.8" }

// The file checkstyle.xml corresponding to the toolVersion has been imported from
// https://github.com/checkstyle/checkstyle/blob/checkstyle-10.13.0/src/main/resources/google_checks.xml
checkstyle {
  toolVersion = "10.13.0"
  isIgnoreFailures = false
  maxWarnings = 0
  maxErrors = 0
  file("$rootDir/config/checkstyle/checkstyle.xml")
}

tasks.withType<Checkstyle>().configureEach {
  reports {
    xml.required = false
    html.required = true
    sarif.required = true
  }
  exclude { element -> element.file.path.contains("build") }
}

dependencyCheck {
  format = "HTML"
  failBuildOnCVSS = "7".toFloat()
  suppressionFile = "$rootDir/config/dependency-check/suppressions.xml"
}

tasks.check { dependsOn("dependencyCheckAnalyze") }
