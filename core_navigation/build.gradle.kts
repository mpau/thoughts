plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
}

android {
    namespace = "com.mpauli.core.navigation"
}

dependencies {
    // Base
    implementation(libs.bundles.base)

    // Navigation
    implementation(libs.androidx.navigation.compose)
}
