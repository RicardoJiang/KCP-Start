pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
    google()
  }
}
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    google()
  }
}

rootProject.name = "KCP-Start"
include(":app")
includeBuild("plugins")

