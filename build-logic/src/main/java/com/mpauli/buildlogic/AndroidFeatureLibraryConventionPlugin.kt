package com.mpauli.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("Unused")
class AndroidFeatureLibraryConventionPlugin : AndroidCoreLibraryConventionPlugin() {

    override fun apply(target: Project) {
        super.apply(target)

        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
            }
        }
    }
}
