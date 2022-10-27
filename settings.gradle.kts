rootProject.name = "ok-accounting-system-202206"

pluginManagement {
    plugins {
        plugins {
            val kotlinVersion: String by settings
            val openapiVersion: String by settings
            val springPluginVersion: String by settings
            val springBootVersion: String by settings
            val springDependencyManagementVersion: String by settings

            id("org.openapi.generator") version openapiVersion apply false
            kotlin("jvm") version kotlinVersion apply false
            id("org.springframework.boot") version springBootVersion
            id("io.spring.dependency-management") version springDependencyManagementVersion
            kotlin("plugin.spring") version springPluginVersion
        }
    }
}
include("ok-accounting-api-v1-jackson")
include("ok-accounting-common")
include("ok-accounting-mappers-v1")
include("ok-accounting-spring")
include("ok-accounting-cor")
include("ok-accounting-business")
include("ok-accounting-kafka")
