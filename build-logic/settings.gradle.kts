pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("buildLibs") {
            from(files("gradle/libs.versions.toml"))
        }
    }
}
