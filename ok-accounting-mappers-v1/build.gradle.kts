plugins {
    kotlin("jvm")
}

dependencies {
    val jUnitJupiterVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":ok-accounting-api-v1-jackson"))
    implementation(project(":ok-accounting-common"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitJupiterVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}