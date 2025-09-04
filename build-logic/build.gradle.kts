plugins {
    `kotlin-dsl`
}

group = "com.mpauli.buildlogic"

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("androidCoreLibraryConvention") {
            id = "com.mpauli.buildlogic.android.core.library.convention"
            implementationClass = "com.mpauli.buildlogic.AndroidCoreLibraryConventionPlugin"
        }
        register("androidFeatureLibraryConvention") {
            id = "com.mpauli.buildlogic.android.feature.library.convention"
            implementationClass = "com.mpauli.buildlogic.AndroidFeatureLibraryConventionPlugin"
        }
    }
}

dependencies {
    implementation(buildLibs.android.build.gradle)
    implementation(buildLibs.kotlin.gradle)
}
