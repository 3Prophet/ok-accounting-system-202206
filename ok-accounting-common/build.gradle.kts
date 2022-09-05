plugins {
    kotlin("jvm")
}

dependencies {
    val datetimeVersion: String by project
    val jUnitJupiterVersion: String by project
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitJupiterVersion")
    api("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
}
