plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.kotlinJpa)
    alias(libs.plugins.springBoot)
    alias(libs.plugins.springDependencyManagement)
    alias(libs.plugins.openApiGenerator)
}

group = "org.clientapp"
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
    dependsOn("generateEurodatClient")
}

tasks.compileJava {
    dependsOn("openApiGenerate")
    dependsOn("generateEurodatClient")
}

openApiGenerate {
    inputSpec.set("$rootDir/clientapp-backend/src/main/resources/backendOpenApi.yaml")
    generatorName.set("kotlin-spring")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
        ),
    )
}

tasks.register(
    "generateEurodatClient",
    org.openapitools.generator.gradle.plugin.tasks
        .GenerateTask::class,
) {
    description = "Task to generate clients for the EuroDaT service."
    group = "clients"
    val eurodatClientDestinationPackage = "org.eurodat"
    input = project.file("$rootDir/clientapp-backend/src/main/resources/eurodat.yaml").path
    outputDir.set(
        layout.buildDirectory
            .dir("clients/eurodat")
            .get()
            .toString(),
    )
    packageName.set(eurodatClientDestinationPackage)
    modelPackage.set("$eurodatClientDestinationPackage.model")
    apiPackage.set("$eurodatClientDestinationPackage.api")
    generatorName.set("java")
    library.set("restclient")

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
    main.java.srcDir(layout.buildDirectory.dir("clients/eurodat/src/main/java"))
}

// For now, exclude all tests since they rely on a running DB service
tasks.test {
    exclude("**/*")
}
