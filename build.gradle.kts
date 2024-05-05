plugins {
    kotlin("jvm") version "1.9.23"
    application
}

group = "com.polimerconsumer"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("com.polimerconsumer.MainKt")
}


repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.ajalt.clikt:clikt:4.2.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.+")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaExec> {
    if (project.hasProperty("appArgs")) {
        args = (project.property("appArgs") as String).split(",")
    }
}