plugins {
    id("com.mpauli.buildlogic.android.feature.library.convention")
}

android {
    namespace = "com.mpauli.feature.thoughtviewer"
}

dependencies {
    implementation(project(":base"))
    implementation(project(":core_app"))
    implementation(project(":core_models"))
    implementation(project(":core_navigation"))
    implementation(project(":core_test"))
    implementation(project(":core_ui"))
    implementation(project(":feature_base"))

    // Base
    implementation(libs.bundles.base)

    // Koin
    implementation(libs.bundles.koin)

    // Logging
    implementation(libs.timber)

    // Material & Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Navigation
    implementation(libs.bundles.navigation)

    // Tests
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.test)

    androidTestImplementation(libs.bundles.android.test)
}
