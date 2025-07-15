import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

val javalinVersion = "6.6.0"
val jteVersion = "3.2.0"
val slf4jVersion = "2.0.13"
val h2Version = "2.3.232"
val hikariVersion = "6.3.0"
val postgresVersion = "42.7.3"
val assertjVersion = "3.27.3"
val logbackVersion = "1.4.11"
val kongVersion = "3.14.5"
val mockVersion = "4.12.0"
val jsoupVersion = "1.17.2"

plugins {
    id("application")
    id ("io.freefair.lombok") version "8.6"
    id("checkstyle")
    id("jacoco")
    id("org.sonarqube") version "6.2.0.5505"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.h2database:h2:$h2Version")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    implementation("gg.jte:jte:$jteVersion")
    implementation("io.javalin:javalin:$javalinVersion")
    implementation("io.javalin:javalin-bundle:$javalinVersion")
    implementation("io.javalin:javalin-rendering:$javalinVersion")
    // implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.konghq:unirest-java:$kongVersion")
    implementation ("org.jsoup:jsoup:$jsoupVersion")

    testImplementation("org.assertj:assertj-core:$assertjVersion")
    testImplementation(platform("org.junit:junit-bom:5.12.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.konghq:unirest-java:$kongVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$mockVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("hexlet.code.App") // Указываем точку входа
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
    finalizedBy(tasks.jacocoTestReport)
    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = true
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // генерируется только после тестов

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}