plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.mpauli.core.app"
}

dependencies {
    implementation(project(":base"))
    implementation(project(":core_apod_api"))
    implementation(project(":core_models"))

    // Coroutines
    implementation(libs.bundles.kotlin.coroutines)

    // Koin
    implementation(libs.bundles.koin)

    // Room
    ksp(libs.androidx.room.compiler)
    implementation(libs.bundles.room)

    // Store
    implementation(libs.store)

    // Tests
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.test)
}
