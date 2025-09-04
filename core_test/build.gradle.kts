plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
}

android {
    namespace = "com.mpauli.core.test"
}

dependencies {
    // Mocking
    implementation(platform(libs.koin.bom))
    implementation(libs.androidx.test.core)
    implementation(libs.koin.android)

    implementation(libs.okhttp.mockwebserver)

    // Tests
    implementation(libs.androidx.core.testing)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.junit5.jupiter.api)
}
