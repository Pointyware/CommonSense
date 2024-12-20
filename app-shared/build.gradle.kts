import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.commonsense.koin)
    alias(libs.plugins.commonsense.kmp)
    alias(libs.plugins.kover)
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_2_0
    }
    jvmToolchain(17)
    jvm {

    }
    androidTarget {

    }
    val framework = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "app_shared"
            isStatic = true
            framework.add(this)
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.core.common)
                implementation(projects.core.data)
                implementation(projects.core.entities)
                implementation(projects.core.interactors)
                implementation(projects.core.local)
                implementation(projects.core.navigation)
                implementation(projects.core.remote)
                implementation(projects.core.ui)
                implementation(projects.core.viewModels)

                implementation(projects.feature.epistemology)
                implementation(projects.feature.ontology)

                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.kotlinx.dateTime)
                implementation(libs.kotlinx.coroutines)
                implementation(libs.kotlinx.io.core)
                implementation(libs.kotlinx.io.bytestring)

                implementation(libs.koin.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val jvmSharedMain by creating {
            dependsOn(commonMain)
        }

        val jvmSharedTest by creating {
            dependsOn(commonTest)
            dependencies {
                implementation(libs.koin.test)

                implementation(libs.mockk)
            }
        }

        val jvmMain by getting {
            dependsOn(jvmSharedMain)
            dependencies {
                implementation(compose.desktop.common)
                implementation(libs.kotlinx.coroutinesSwing)
            }
        }

        val jvmTest by getting {
            dependsOn(jvmSharedTest)
        }

        val androidMain by getting {
            dependsOn(jvmSharedMain)
        }
        val androidUnitTest by getting {
            dependsOn(jvmSharedTest)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.commonsense.shared"
    generateResClass = always
}

android {
    namespace = "org.pointyware.commonsense.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
}
