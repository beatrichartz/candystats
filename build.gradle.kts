import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.4.10"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("io.micronaut.application") version "1.0.3"
}

version "0.1"
group "com.retail"

repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.retail.*")
    }
}
dependencies {
    val kotlinVersion = "1.4.10"

    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut:micronaut-inject-java")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    runtimeOnly("org.slf4j:slf4j-simple")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.postgresql:postgresql:42.2.18")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")


    testAnnotationProcessor("io.micronaut:micronaut-inject-java")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("io.micronaut.test:micronaut-test-junit5:2.1.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")

    testImplementation("org.assertj:assertj-core:3.17.2")
    testImplementation("org.skyscreamer:jsonassert:1.5.0")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test>() {
    useJUnitPlatform()
}

application {
    mainClassName = "com.candystats.ApplicationKt"
}


