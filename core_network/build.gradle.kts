plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
    alias(libs.plugins.secrets.gradle)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mpauli.core.network"

    buildFeatures { buildConfig = true }

    defaultConfig {
        buildConfigField("String", "NASA_BASE_URL", "\"https://api.nasa.gov/\"")
    }
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    // Koin
    implementation(libs.bundles.koin)

    // Ktor & Serialization
    implementation(libs.bundles.ktor.serialization)

    // Logging
    implementation(libs.timber)

    // OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
}
