plugins {
    kotlin("jvm") version "1.9.0"
}

group = "mcburney.aoc.twenty.three"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}