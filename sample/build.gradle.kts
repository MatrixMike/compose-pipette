import org.jetbrains.compose.resources.ResourcesExtension
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("kmp-base")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.jb)
    alias(libs.plugins.android.application)
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    androidTarget()

    wasmJs {
        moduleName = "sample"
        browser {
            commonWebpackConfig {
                outputFileName = "sample.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core)
                implementation(projects.util)

                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)

                implementation(libs.materialKolor)
                implementation(libs.settings)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.androidx.activity)
                implementation(libs.appcompat)
            }
        }

        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
    }
}

android {
    namespace = "dev.zt64.compose.color.sample"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        targetSdk = 35
    }
}

compose {
    resources {
        generateResClass = ResourcesExtension.ResourceClassGeneration.Never
    }

    desktop {
        application {
            mainClass = "dev.zt64.compose.color.sample.MainKt"
        }
    }
}