import io.gitlab.arturbosch.detekt.Detekt

plugins {
    val kotlinVersion = "1.6.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.18.0"
    id("org.jmailen.kotlinter") version "3.6.0"
    id("com.google.osdetector") version "1.7.0"
}

group = "me.dgahn"
version = "0.0.1-SNAPSHOT"

repositories {
    google()
    mavenCentral()
}

kotlinter {
    ignoreFailures = false
    indentSize = 4
    reporters = arrayOf("checkstyle", "plain")
    experimentalRules = false
    disabledRules = emptyArray()
}

dependencies {
    if (osdetector.arch == "aarch_64") {
        implementation("io.netty:netty-resolver-dns-native-macos:4.1.77.Final:osx-aarch_64")
    }

    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("io.projectreactor.netty:reactor-netty:1.0.17")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    val slackVersion = "1.26.0"
    implementation("com.slack.api:slack-api-client:$slackVersion")
    implementation("com.slack.api:slack-api-model-kotlin-extension:$slackVersion")
    implementation("com.slack.api:slack-api-client-kotlin-extension:$slackVersion")

    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.6.21")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
        dependsOn(detekt)
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    withType<Detekt> {
        dependsOn(formatKotlin)
    }
}
