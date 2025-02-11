pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

rootProject.name = "AbnRepoViewer"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core:domain")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:data")
include(":repo-viewer:presentation")
include(":repo-viewer:domain")
include(":repo-viewer:data")
include(":core:database")
