plugins {
  kotlin("jvm") version "2.0.0-Beta4"
  kotlin("plugin.serialization") version "2.0.0-Beta4"
  id("java")
}

group = "com.dream"
version = "1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation("it.unimi.dsi:fastutil:8.5.8")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2")
  //implementation("org.openjdk.jol:jol-core:0.9")
}

kotlin {
  sourceSets.all {
    languageSettings {
      languageVersion = "2.0"
    }
  }
  jvmToolchain(8)
}

