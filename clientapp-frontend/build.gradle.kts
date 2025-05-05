group = "org.clientapp"

version = "0.0.1-SNAPSHOT"

repositories { mavenCentral() }

dependencyCheck {
  format = "HTML"
  failBuildOnCVSS = "7".toFloat()
  suppressionFile = "$rootDir/config/dependency-check/suppressions.xml"
}

tasks.check { dependsOn("dependencyCheckAnalyze") }
