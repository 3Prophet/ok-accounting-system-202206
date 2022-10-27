plugins {
    kotlin("jvm")
}


dependencies {
    val jUnitJupiterVersion: String by project
    val coroutinesVersion: String by project

    implementation(kotlin("stdlib"))
    implementation(project(":ok-accounting-common"))

    implementation(kotlin("test-common"))
    implementation(kotlin("test-annotations-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitJupiterVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}