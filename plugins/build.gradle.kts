buildscript {
  extra["kotlin_plugin_id"] = "com.zj.debuglog.kotlin-plugin"
}

plugins {
  kotlin("jvm") version "1.7.10" apply false
  id("org.jetbrains.dokka") version "1.7.10" apply false
  id("com.gradle.plugin-publish") version "1.0.0" apply false
  id("com.github.gmazzo.buildconfig") version "3.1.0" apply false
}

allprojects {
  group = "com.zj.debuglog"
  version = "0.1.0-SNAPSHOT"
}

subprojects {
  repositories {
    mavenCentral()
  }
}
