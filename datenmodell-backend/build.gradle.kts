plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.kotlinJpa)
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.openApiGenerator)
}

group = "org.datenmodell"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

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
    runtimeOnly(libs.h2Database)
    runtimeOnly(libs.postgresDriver)
    testImplementation(libs.springBootTest)
    testImplementation(libs.kotlinJunit5)
    testImplementation(libs.jupiter)
    testRuntimeOnly(libs.junitLauncher)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileKotlin {
    dependsOn("openApiGenerate")
    dependsOn("generateEurodatAppClient")
    dependsOn("generateEurodatControllerClient")
    dependsOn("generateEurodatDataManagementClient")
    dependsOn("generateEurodatDatabaseClient")
}

tasks.compileJava {
    dependsOn("openApiGenerate")
    dependsOn("generateEurodatAppClient")
    dependsOn("generateEurodatControllerClient")
    dependsOn("generateEurodatDataManagementClient")
    dependsOn("generateEurodatDatabaseClient")
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

tasks.register(
    "generateEurodatAppClient",
    org.openapitools.generator.gradle.plugin.tasks
        .GenerateTask::class,
) {
    description = "Task to generate clients for the EuroDaT app service."
    group = "clients"
    val eurodatClientDestinationPackage = "org.eurodat.eurodatapp.openApiClient"
    input =
        project
            .file("$rootDir/datenmodell-backend/src/main/resources/eurodatApp.yaml")
            .path
    outputDir.set(
        layout.buildDirectory
            .dir("clients/eurodat-app")
            .get()
            .toString(),
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
    org.openapitools.generator.gradle.plugin.tasks
        .GenerateTask::class,
) {
    description = "Task to generate clients for the EuroDaT controller service."
    group = "clients"
    val eurodatClientDestinationPackage = "org.eurodat.eurodatcontroller.openApiClient"
    input =
        project
            .file("$rootDir/datenmodell-backend/src/main/resources/eurodatController.yaml")
            .path
    outputDir.set(
        layout.buildDirectory
            .dir("clients/eurodat-controller")
            .get()
            .toString(),
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
    org.openapitools.generator.gradle.plugin.tasks
        .GenerateTask::class,
) {
    description = "Task to generate clients for the EuroDaT data management service."
    group = "clients"
    val eurodatClientDestinationPackage = "org.eurodat.eurodatdatamanagement.openApiClient"
    input =
        project
            .file("$rootDir/datenmodell-backend/src/main/resources/eurodatDataManagement.yaml")
            .path
    outputDir.set(
        layout.buildDirectory
            .dir("clients/eurodat-data-management")
            .get()
            .toString(),
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
    org.openapitools.generator.gradle.plugin.tasks
        .GenerateTask::class,
) {
    description = "Task to generate clients for the EuroDaT database service."
    group = "clients"
    val eurodatClientDestinationPackage = "org.eurodat.eurodatdatabase.openApiClient"
    input =
        project
            .file("$rootDir/datenmodell-backend/src/main/resources/eurodatDatabase.yaml")
            .path
    outputDir.set(
        layout.buildDirectory
            .dir("clients/eurodat-database")
            .get()
            .toString(),
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

sourceSets {
    val main by getting
    main.kotlin.srcDir(layout.buildDirectory.dir("generate-resources/main/src/main/kotlin"))
    main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-app/src/main/java"))
    main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-controller/src/main/java"))
    main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-data-management/src/main/java"))
    main.java.srcDir(layout.buildDirectory.dir("clients/eurodat-database/src/main/java"))
}

// For now, exclude all tests since they rely on a running DB service
tasks.test {
    exclude("**/*")
}
