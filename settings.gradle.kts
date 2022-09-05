rootProject.name = "ok-accounting-system-202206"

pluginManagement {
    plugins {
        plugins {
            val kotlinVersion: String by settings
            val openapiVersion: String by settings

            id("org.openapi.generator") version openapiVersion apply false
            kotlin("jvm") version kotlinVersion apply false
        }
    }
}
include("ok-accounting-api-v1-jackson")
include("ok-accounting-common")
include("ok-accounting-mappers-v1")
include("ok-accounting-spring")
