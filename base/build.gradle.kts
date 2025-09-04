plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
}

android {
    namespace = "com.mpauli.base"
}

dependencies {
    // Base
    implementation(libs.bundles.base)

    // Koin
    implementation(libs.bundles.koin)

    // Room
    implementation(libs.androidx.room.runtime)

    // Tests
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.test)
}
