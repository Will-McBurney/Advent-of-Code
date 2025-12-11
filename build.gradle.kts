plugins {
    kotlin("jvm") version "2.2.0"
}

group = "mcburney.aoc.twenty.three"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20231013")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.9.24")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("org.choco-solver:choco-solver:4.10.14")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}