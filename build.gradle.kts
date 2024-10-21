@file:Suppress("OPT_IN_USAGE")

import org.gradle.internal.impldep.org.junit.Test
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.power-assert") version "2.0.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val coroutinesVersion = "1.9.0-RC"

dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("test-junit5"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation("com.sksamuel.aedile:aedile-core:1.3.1") // Needed for CompanyDetailsRepository
}

tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs = listOf("-Xcontext-receivers", "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
}

java.sourceSets["test"].java {
    srcDir("src/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

powerAssert {
    functions = listOf("kotlin.assert", "kotlin.test.assertEquals")
}

kotlin {
    jvmToolchain(11)
}
