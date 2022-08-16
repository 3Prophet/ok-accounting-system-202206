plugins {
    kotlin("jvm")

}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":ok-accounting-api-v1-jackson"))
    implementation(project(":ok-accounting-common"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}