plugins {
    kotlin("jvm")
}


dependencies {
    val kafkaVersion: String by project
    val jUnitJupiterVersion: String by project
    val coroutinesVersion: String by project
    val atomicfuVersion: String by project
    val logbackVersion: String by project
    val kotlinLoggingJvmVersion: String by project

    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("org.jetbrains.kotlinx:atomicfu:$atomicfuVersion")
    implementation(project(":ok-accounting-common"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
    implementation(kotlin("stdlib"))
    implementation(project(":ok-accounting-api-v1-jackson"))
    implementation(project(":ok-accounting-common"))
    implementation(project(":ok-accounting-business"))
    implementation(project(":ok-accounting-mappers-v1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitJupiterVersion")
    // log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingJvmVersion")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}