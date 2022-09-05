import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.3"
	id("io.spring.dependency-management") version "1.0.13.RELEASE"
	kotlin("jvm")
	kotlin("plugin.spring") version "1.6.21"
}

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	val springDocVersion = "1.6.11"

	implementation(project(":ok-accounting-api-v1-jackson"))
	implementation(project(":ok-accounting-common"))
	implementation(project(":ok-accounting-mappers-v1"))
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
