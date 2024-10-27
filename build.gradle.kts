plugins {
  kotlin("jvm") version "2.0.21"
  kotlin("plugin.serialization") version "2.0.21"
  id("java")
  id("maven-publish")
}

group = "harmony.nbt"
version = "1.0"

repositories {
  mavenCentral()
}

dependencies {
  implementation("it.unimi.dsi:fastutil:8.5.13")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
  implementation("com.github.luben:zstd-jni:1.5.6-6")
}

kotlin {
  jvmToolchain(8)
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      from(components["kotlin"])
    }
  }
}
