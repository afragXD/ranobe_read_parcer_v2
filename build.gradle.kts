import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-client-core:3.0.3")
    implementation("io.ktor:ktor-client-cio:3.0.3")
    implementation("io.ktor:ktor-client-content-negotiation:3.0.3")
    implementation("io.ktor:ktor-serialization-gson:3.0.3")
    implementation("org.jsoup:jsoup:1.15.3")
    implementation("ch.qos.logback:logback-classic:1.4.12")

    implementation("org.jetbrains.exposed:exposed-core:0.51.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.51.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.51.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
    implementation("org.postgresql:postgresql:42.7.2") // Use the latest version
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}