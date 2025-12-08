group = "org.datenmodell"

version = "0.0.1-SNAPSHOT"

repositories { mavenCentral() }

dependencyCheck {
  format = "HTML"
  failBuildOnCVSS = "7".toFloat()
  suppressionFile = "$rootDir/config/dependency-check/suppressions.xml"
}

// TODO Fix failing NVD download,see
// https://github.com/dependency-check/DependencyCheck/issues/6107
// tasks.check { dependsOn("dependencyCheckAnalyze") }
