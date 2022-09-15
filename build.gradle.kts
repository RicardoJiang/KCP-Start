plugins {
  id("com.android.application") version "7.2.1" apply false
  id("com.android.library") version "7.2.1" apply false
  id("org.jetbrains.kotlin.android") version "1.7.10" apply false
  id("com.bnorm.template.kotlin-ir-plugin") apply false
}

tasks.register<Delete>(name = "clean") {
  group = "build"
  delete(rootProject.buildDir)
}
