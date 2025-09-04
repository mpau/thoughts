plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mpauli.core.ui"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":base"))

    // Base
    implementation(libs.bundles.base)

    // Coil
    implementation(libs.bundles.compose.image)

    // Material & Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
