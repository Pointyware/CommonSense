package org.pointyware.commonsense.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets

/**
 * A plugin that sets up the KMP targets convention for the project.
 */
class ComposeTargetsConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.compose")
        target.plugins.apply("org.jetbrains.kotlin.plugin.compose")

        // Add dependencies after evaluation
        target.afterEvaluate {
            dependencies {
                /*

                implementation(compose.ui)
                implementation(compose.material3)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                 */
            }
        }
    }
}
