import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    gradlePluginPortal()
}

dependencies {
    val springDocVersion: String by project

    implementation(project(":ok-accounting-api-v1-jackson"))
    implementation(project(":ok-accounting-common"))
    implementation(project(":ok-accounting-mappers-v1"))
    implementation(project(":ok-accounting-business"))
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:${springDocVersion}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${springDocVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks {
    withType<ProcessResources> {
        from("$rootDir/open-api-spec") {
            into("/static")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
