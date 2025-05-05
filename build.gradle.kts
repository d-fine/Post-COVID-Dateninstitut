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

dependencyCheck {
  format = "HTML"
  failBuildOnCVSS = "7".toFloat()
  suppressionFile = "$rootDir/config/dependency-check/suppressions.xml"
}

tasks.check { dependsOn("dependencyCheckAggregate") }
