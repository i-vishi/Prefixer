// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.diffplug.spotless) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}


subprojects {
    apply(plugin = "com.diffplug.spotless")
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlinGradle {
            target("*.gradle.kts")
            ktlint()
        }

        kotlin {
            target ("**/*.kt")
            targetExclude("$buildDir/**/*.kt")
            targetExclude("bin/**/*.kt")
            targetExclude("**/*.json")

            trimTrailingWhitespace()
            ktlint("0.49.0")
        }

        java {
            target ("src/*/java/**/*.java")
            targetExclude("$buildDir/**/*.java")
            targetExclude("bin/**/*.java")
            targetExclude("**/*.json")
            trimTrailingWhitespace()
            googleJavaFormat("1.11.0").aosp()
        }
    }
}
