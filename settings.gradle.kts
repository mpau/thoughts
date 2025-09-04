pluginManagement {
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

    includeBuild("build-logic")
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("buildLibs") {
            from(files("build-logic/gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "Thoughts"

// App entry point
include(":app")

// Core modules
include(":base")
include(":core_apod_api")
include(":core_app")
include(":core_models")
include(":core_navigation")
include(":core_network")
include(":core_test")
include(":core_ui")

// Feature modules
include(":feature_base")
include(":feature_day_screen")
include(":feature_overview_screen")
include(":feature_perspective_screen")
include(":feature_thought_viewer")
