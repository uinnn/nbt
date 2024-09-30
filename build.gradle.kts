plugins {
  kotlin("jvm") version "2.0.20"
  kotlin("plugin.serialization") version "2.0.20"
  id("java")
}

group = "com.dream"
version = "1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation("it.unimi.dsi:fastutil:8.5.13")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
}

kotlin {
  jvmToolchain(8)
}

