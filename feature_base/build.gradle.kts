plugins {
    id("com.mpauli.buildlogic.android.feature.library.convention")
}

android {
    namespace = "com.mpauli.feature.base"
}

dependencies {
    implementation(project(":base"))

    // Base
    implementation(libs.bundles.base)

    // Logging
    implementation(libs.timber)

    // Material & Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose.lite)

    // Tests
    testImplementation(project(":core_test"))

    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.test)
}
