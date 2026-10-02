plugins {
    kotlin("jvm") version "2.4.0"
    id("com.chaquo.python") version "17.0.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.25"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(26)
}

tasks.test {
    useJUnitPlatform()
}