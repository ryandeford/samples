plugins {
    id("java")
}

group = "io.ryandeford.samples"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    // This enables basic test detail output
    // Note: Detailed HTML reports can be reviewed at ./build/reports/tests/test/index.html
    testLogging {
        events("passed", "skipped", "failed")
    }
}