plugins {
    id("com.mpauli.buildlogic.android.core.library.convention")
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mpauli.core.apodapi"
}

dependencies {
    implementation(project(":base"))
    implementation(project(":core_models"))
    implementation(project(":core_network"))

    // Koin
    implementation(libs.bundles.koin)

    // Ktorfit
    implementation(libs.ktorfit)

    // Tests
    testImplementation(project(":core_test"))

    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.bundles.test)

    testImplementation(libs.ktor.client.cio)
    testImplementation(libs.bundles.ktor.serialization)
    testImplementation(libs.okhttp.mockwebserver)
}
