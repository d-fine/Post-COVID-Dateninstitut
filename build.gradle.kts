plugins {
  alias(libs.plugins.kotlinJvm)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.detekt)
  checkstyle
  alias(libs.plugins.dependencyCheck)
}

repositories { mavenCentral() }

subprojects {
  plugins.apply("org.jetbrains.kotlin.jvm")
  plugins.apply("com.ncorti.ktfmt.gradle")
  plugins.apply("io.gitlab.arturbosch.detekt")
  plugins.apply("checkstyle")
  plugins.apply("org.owasp.dependencycheck")
}

tasks.check { dependsOn("dependencyCheckAggregate") }
