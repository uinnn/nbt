plugins {
  kotlin("jvm") version "2.0.0-Beta2"
  id("java")
}

group = "com.dream"
version = "1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation("it.unimi.dsi:fastutil:8.5.8")
}

kotlin {
  sourceSets.all {
    languageSettings {
      languageVersion = "2.0"
    }
  }
  jvmToolchain(8)
}

