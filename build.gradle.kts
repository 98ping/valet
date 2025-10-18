plugins {
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "9.2.2"
}

group = "me.max.valet"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("io.github.revxrsal:lamp.common:4.0.0-rc.5")
    implementation("io.github.revxrsal:lamp.cli:4.0.0-rc.5")
    implementation("com.github.nazmulidris:color-console:1.0.0")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        manifest {
            attributes["Main-Class"] = "me.max.valet.Valet"
        }
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}