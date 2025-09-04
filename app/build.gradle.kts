plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.mpauli.thoughts"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.mpauli.thoughts"
        minSdk = 26
        targetSdk = 36
        versionCode = System.getenv("TC_BUILD_NUMBER")?.toInt() ?: 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        debug {
            buildConfigField(
                "Boolean",
                "ENABLE_LOGGING",
                "true"
            )
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }

    packaging {
        resources {
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(project(":base"))
    implementation(project(":core_app"))
    implementation(project(":core_apod_api"))
    implementation(project(":core_navigation"))
    implementation(project(":core_network"))
    implementation(project(":core_ui"))
    implementation(project(":feature_day_screen"))
    implementation(project(":feature_overview_screen"))
    implementation(project(":feature_thought_viewer"))
    implementation(project(":feature_perspective_screen"))

    // Base
    implementation(libs.bundles.base)

    // Material & Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Navigation
    implementation(libs.bundles.navigation)

    // Koin
    implementation(libs.bundles.koin)

    // Logging
    implementation(libs.timber)

    // Tests
    androidTestImplementation(libs.bundles.android.test)
    androidTestImplementation(libs.kluent.android)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
