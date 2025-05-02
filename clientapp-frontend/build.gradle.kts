group = "org.clientapp"

version = "0.0.1-SNAPSHOT"

repositories { mavenCentral() }

dependencyCheck {
  format = "HTML"
  failBuildOnCVSS =
      "11".toFloat() // TODO With "11" it will never fail, with "0" if there are any vulnerabilities
}

tasks.check { dependsOn("dependencyCheckAnalyze") }
