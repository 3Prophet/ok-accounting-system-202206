plugins {
    kotlin("jvm")
}


dependencies {
    val jUnitJupiterVersion: String by project
    val coroutinesVersion: String by project
    implementation(project(":ok-accounting-common"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation(kotlin("stdlib"))
    implementation(project(":ok-accounting-api-v1-jackson"))
    implementation(project(":ok-accounting-common"))
    implementation(project(":ok-accounting-cor"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitJupiterVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}