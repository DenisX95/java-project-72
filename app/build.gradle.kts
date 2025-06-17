plugins {
    id("java")
    id ("io.freefair.lombok") version "8.6"
    id("checkstyle")
    id("jacoco")
    id("org.sonarqube") version "6.2.0.5505"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"
val assertjVersion = "3.24.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:$assertjVersion")
}

checkstyle {
    toolVersion = "10.12.4"
    configFile = file("${rootProject.projectDir}/config/checkstyle/checkstyle.xml")
    isIgnoreFailures = true
}

sonar {
    properties {
        property("sonar.projectKey", "DenisX95_java-project-72")
        property("sonar.organization", "denisx95")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
    }
}